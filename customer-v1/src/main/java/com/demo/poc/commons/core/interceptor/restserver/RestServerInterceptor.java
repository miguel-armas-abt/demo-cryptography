package com.demo.poc.commons.core.interceptor.restserver;

import static com.demo.poc.commons.core.interceptor.restserver.RestServerInterceptor.RequestLoggerUtil.*;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.demo.poc.commons.core.logging.ThreadContextInjector;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@WebFilter(urlPatterns = "/*")
@RequiredArgsConstructor
public class RestServerInterceptor implements Filter {

  private static final List<String> EXCLUDED_PATHS = List.of("/h2-console", "/swagger-ui", "/actuator");

  private final ThreadContextInjector threadContextInjector;

  @Override
  public void init(FilterConfig filterConfig) {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    String requestUri = httpRequest.getRequestURI();
    if (EXCLUDED_PATHS.stream().anyMatch(requestUri::startsWith)) {
      chain.doFilter(request, response);
      return;
    }

    BufferingHttpServletResponse bufferingResponse = new BufferingHttpServletResponse((HttpServletResponse) response);
    BufferingHttpServletRequest bufferingRequest = new BufferingHttpServletRequest(httpRequest);

    generateTraceOfRequest(bufferingRequest);

    chain.doFilter(bufferingRequest, bufferingResponse);
    String responseBody = bufferingResponse.getCachedBody();

    generateTraceOfResponse(bufferingResponse, extractRequestURL(httpRequest), responseBody);

    httpResponse.getOutputStream().write(responseBody.getBytes(StandardCharsets.UTF_8));
  }

  @Override
  public void destroy() {
  }

  private void generateTraceOfRequest(HttpServletRequest request) {
    threadContextInjector.populateFromRestServerRequest(
        request.getMethod(),
        extractRequestURL(request),
        extractRequestHeadersAsMap(request),
        extractRequestBody(request));
  }

  private void generateTraceOfResponse(HttpServletResponse response, String uri, String responseBody) {
    threadContextInjector.populateFromRestServerResponse(
        ResponseLoggerUtil.extractResponseHeadersAsMap(response),
        uri,
        responseBody,
        String.valueOf(response.getStatus()));
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class ExclusionUtil {

    public static void excludeUris(FilterChain chain, ServletRequest request,
                                   ServletResponse response, String requestUri) throws IOException, ServletException {

    }
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class RequestLoggerUtil {
    public static Map<String, String> extractRequestHeadersAsMap(HttpServletRequest request) {
      return Optional.ofNullable(request.getHeaderNames())
          .map(Collections::list)
          .orElseGet(ArrayList::new)
          .stream()
          .collect(Collectors.toMap(headerName -> headerName, request::getHeader));
    }

    public static String extractRequestBody(HttpServletRequest request) {
      try {
        return new BufferingHttpServletRequest(request).getRequestBody();
      } catch (IOException exception) {
        log.error("Error reading request body: {}", exception.getClass(), exception);
        return StringUtils.EMPTY;
      }
    }

    public static String extractRequestURL(HttpServletRequest request) {
      String url = Optional.of(request)
          .map(HttpServletRequest::getRequestURL)
          .map(StringBuffer::toString)
          .orElse(StringUtils.EMPTY);

      String queryString = request.getQueryString();
      if (StringUtils.isNotBlank(queryString))
        url += "?" + queryString;

      return url;
    }
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  private static class ResponseLoggerUtil {

    public static Map<String, String> extractResponseHeadersAsMap(HttpServletResponse response) {
      Map<String, String> headers = new HashMap<>();
      Collection<String> headerNames = response.getHeaderNames();
      headerNames.forEach(headerName -> headers.put(headerName, response.getHeader(headerName)));
      return headers;
    }

  }
}
