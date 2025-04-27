package com.demo.poc.commons.core.interceptor.restclient.request;

import com.demo.poc.commons.core.logging.dto.RestRequestLog;
import com.demo.poc.commons.core.logging.enums.LoggingType;
import com.demo.poc.commons.core.logging.ThreadContextInjector;
import com.demo.poc.commons.core.tracing.enums.TraceParam;
import com.demo.poc.commons.custom.properties.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.demo.poc.commons.core.logging.enums.LoggingType.REST_CLIENT_REQ;

@Slf4j
@RequiredArgsConstructor
public class RestClientRequestInterceptor implements ClientHttpRequestInterceptor {

  private final ThreadContextInjector contextInjector;
  private final ApplicationProperties properties;

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    generateTrace(request, body);
    return execution.execute(request, body);
  }

  private void generateTrace(HttpRequest request, byte[] body) {
    if (properties.isLoggerPresent(REST_CLIENT_REQ)) {
      RestRequestLog log = RestRequestLog.builder()
          .method(request.getMethod().toString())
          .uri(request.getURI().toString())
          .requestHeaders(request.getHeaders().toSingleValueMap())
          .requestBody(new String(body, StandardCharsets.UTF_8))
          .traceParent(request.getHeaders().getFirst(TraceParam.TRACE_PARENT.getKey()))
          .build();

      contextInjector.populateFromRestRequest(LoggingType.REST_CLIENT_REQ, log);
    }
  }
}