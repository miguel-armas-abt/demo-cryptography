package com.demo.poc.commons.core.restclient.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@Builder
public class ExchangeRequest<I, O> implements Serializable {

  private String url;

  private HttpMethod httpMethod;

  private Map<String, String> headers;

  private Map<String, String> uriVariables;

  private I requestBody;

  private Class<O> responseClass;

  private Class<?> errorWrapperClass;
}
