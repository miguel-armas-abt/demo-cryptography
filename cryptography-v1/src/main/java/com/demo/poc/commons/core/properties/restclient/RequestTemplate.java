package com.demo.poc.commons.core.properties.restclient;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class RequestTemplate {

  private String endpoint;
  private HeaderTemplate headers;
  private Map<String, String> formData;
}
