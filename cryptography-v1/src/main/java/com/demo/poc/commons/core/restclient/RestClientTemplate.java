package com.demo.poc.commons.core.restclient;

import java.util.Objects;

import com.demo.poc.commons.core.errors.dto.ErrorDto;
import com.demo.poc.commons.core.errors.exceptions.RestClientException;
import com.demo.poc.commons.core.properties.restclient.HeaderTemplate;
import com.demo.poc.commons.core.restclient.dto.ExchangeRequest;
import com.demo.poc.commons.core.restclient.utils.HttpHeadersFiller;
import com.demo.poc.commons.custom.properties.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class RestClientTemplate {

  private final RestTemplate restTemplate;
  private final ApplicationProperties properties;

  public <I, O> O exchange(ExchangeRequest<I, O> request, String serviceName) {
    try {
      return Objects.nonNull(request.getUriVariables())
          ? restTemplate.exchange(request.getUrl(),
              request.getHttpMethod(),
              createHttpEntity(request, properties.getRestClients().get(serviceName).getRequest().getHeaders()),
              request.getResponseClass(),
              request.getUriVariables())
          .getBody()

          : restTemplate.exchange(request.getUrl(),
              request.getHttpMethod(),
              createHttpEntity(request, properties.getRestClients().get(serviceName).getRequest().getHeaders()),
              request.getResponseClass())
          .getBody();

    } catch (HttpStatusCodeException httpException) {
      throw handleException(httpException);
    }
  }

  private static <I, O> HttpEntity<I> createHttpEntity(ExchangeRequest<I, O> request,
                                                       HeaderTemplate headerTemplate) {
    HttpHeaders headers = HttpHeadersFiller.generateHeaders(headerTemplate, request.getHeaders());
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<>(request.getRequestBody(), headers);
  }

  private RestClientException handleException(HttpStatusCodeException httpException) {
    String jsonBody = httpException.getResponseBodyAsString();
    return StringUtils.EMPTY.equals(jsonBody)
        ? new RestClientException(ErrorDto.CODE_DEFAULT, "Unexpected", HttpStatusCode.valueOf(409))
        : new RestClientException(ErrorDto.CODE_DEFAULT, jsonBody, httpException.getStatusCode());
  }
}
