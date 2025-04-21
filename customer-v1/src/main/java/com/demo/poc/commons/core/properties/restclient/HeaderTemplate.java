package com.demo.poc.commons.core.properties.restclient;

import com.demo.poc.commons.core.tracing.enums.TraceParamType;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class HeaderTemplate {

  private Map<String, String> provided;
  private Map<String, TraceParamType> generated;
  private Map<String, String> forwarded;

}
