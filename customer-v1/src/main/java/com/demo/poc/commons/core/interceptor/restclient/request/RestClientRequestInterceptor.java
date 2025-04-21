package com.demo.poc.commons.core.interceptor.restclient.request;

import com.demo.poc.commons.core.logging.ThreadContextInjector;
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

  private final ThreadContextInjector threadContextInjector;

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    generateTrace(request, body);
    return execution.execute(request, body);
  }

  private void generateTrace(HttpRequest request, byte[] body) {
    threadContextInjector.populateFromRestClientRequest(
        request.getMethod().toString(),
        request.getURI().toString(),
        request.getHeaders().toSingleValueMap(),
        new String(body, StandardCharsets.UTF_8)
    );
  }
}
