package com.demo.poc.commons.core.interceptor.restclient.request;

import com.demo.poc.commons.core.logging.RestClientThreadContextInjector;
import com.demo.poc.commons.core.logging.dto.RestRequestLog;
import com.demo.poc.commons.core.tracing.enums.TraceParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class RestClientRequestInterceptor implements ClientHttpRequestInterceptor {

  private final RestClientThreadContextInjector restClientContext;

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    generateTrace(request, body);
    return execution.execute(request, body);
  }

  private void generateTrace(HttpRequest request, byte[] body) {
    RestRequestLog log = RestRequestLog.builder()
        .method(request.getMethod().toString())
        .uri(request.getURI().toString())
        .requestHeaders(request.getHeaders().toSingleValueMap())
        .requestBody(new String(body, StandardCharsets.UTF_8))
        .traceParent(request.getHeaders().getFirst(TraceParam.TRACE_PARENT.getKey()))
        .build();

    restClientContext.populateRequest(log);
  }
}
