package com.demo.poc.commons.core.properties.logging;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class LoggingTemplate {

  private Map<String, Boolean> loggingType;
  private ObfuscationTemplate obfuscation;
}