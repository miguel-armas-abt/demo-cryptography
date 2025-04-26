package com.demo.poc.commons.core.interceptor.restclient.response;

import com.demo.poc.commons.core.logging.ThreadContextInjector;
import com.demo.poc.commons.core.logging.dto.RestResponseLog;
import com.demo.poc.commons.core.logging.enums.LoggingType;
import com.demo.poc.commons.core.tracing.enums.TraceParam;
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

  private final ThreadContextInjector contextInjector;

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                      ClientHttpRequestExecution execution) throws IOException {
    ClientHttpResponse response = execution.execute(request, body);
    return generateTrace(request, response);
  }

  private ClientHttpResponse generateTrace(HttpRequest request, ClientHttpResponse response) {
    try {
      BufferingClientHttpResponse bufferedResponse = new BufferingClientHttpResponse(response);
      String responseBody = StreamUtils.copyToString(bufferedResponse.getBody(), StandardCharsets.UTF_8);

      RestResponseLog log = RestResponseLog.builder()
          .responseHeaders(response.getHeaders().toSingleValueMap())
          .uri(request.getURI().toString())
          .responseBody(responseBody)
          .httpCode(String.valueOf(response.getStatusCode().value()))
          .traceParent(request.getHeaders().getFirst(TraceParam.TRACE_PARENT.getKey()))
          .build();

      contextInjector.populateFromRestResponse(LoggingType.REST_CLIENT_RES, log);
      return bufferedResponse;

    } catch (IOException exception) {
      log.error("Response body error: {}", exception.getClass(), exception);
    }
    return response;
  }
}
