package com.demo.poc.commons.core.logging.dto;

import java.io.Serializable;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RestRequestLog implements Serializable {

  private String method;
  private String uri;
  private Map<String, String> requestHeaders;
  private String requestBody;
  private String traceParent;
}
