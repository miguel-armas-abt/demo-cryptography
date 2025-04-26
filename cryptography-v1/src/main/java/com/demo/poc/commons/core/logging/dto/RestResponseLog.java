package com.demo.poc.commons.core.logging.dto;

import java.io.Serializable;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RestResponseLog implements Serializable {

  private String method;
  private String httpCode;
  private String uri;
  private Map<String, String> responseHeaders;
  private String responseBody;
  private String traceParent;
}
