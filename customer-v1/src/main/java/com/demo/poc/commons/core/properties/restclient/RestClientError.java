package com.demo.poc.commons.core.properties.restclient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RestClientError {

  private String customCode;
  private String message;
  private Integer httpCode;
}