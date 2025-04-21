package com.demo.poc.commons.core.interceptor.restclient.response;

import com.demo.poc.commons.core.logging.ThreadContextInjector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class RestClientResponseInterceptor implements ClientHttpRequestInterceptor {

  private final ThreadContextInjector threadContextInjector;

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                      ClientHttpRequestExecution execution) throws IOException {
    ClientHttpResponse response = execution.execute(request, body);
    return generateTrace(response, request.getURI().toString());
  }

  private ClientHttpResponse generateTrace(ClientHttpResponse response, String uri) {
    try {
      BufferingClientHttpResponse bufferedResponse = new BufferingClientHttpResponse(response);
      String responseBody = StreamUtils.copyToString(bufferedResponse.getBody(), StandardCharsets.UTF_8);

      threadContextInjector.populateFromRestClientResponse(
          response.getHeaders().toSingleValueMap(),
          uri,
          responseBody,
          getHttpCode(response));
      return bufferedResponse;

    } catch (Exception exception) {
      log.error("Error reading response body: {}", exception.getClass(), exception);
    }
    return response;
  }

  private static String getHttpCode(ClientHttpResponse response) throws IOException {
    try {
      return response.getStatusCode().toString();
    } catch (IOException ex) {
      return String.valueOf(response.getStatusCode());
    }
  }
}
