package com.demo.poc.commons.core.properties;

import com.demo.poc.commons.core.errors.exceptions.NoSuchRestClientException;
import com.demo.poc.commons.core.logging.enums.LoggingType;
import com.demo.poc.commons.core.properties.logging.LoggingTemplate;
import com.demo.poc.commons.core.properties.logging.ObfuscationTemplate;
import com.demo.poc.commons.core.properties.restclient.RestClient;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
public abstract class ConfigurationBaseProperties {

  protected ProjectType projectType;

  protected LoggingTemplate logging;

  protected Map<String, String> errorMessages;

  protected Map<String, RestClient> restClients;

  public RestClient searchRestClient(String serviceName) {
    return Optional.ofNullable(restClients.get(serviceName))
        .orElseThrow(() -> new NoSuchRestClientException(serviceName));
  }

  public boolean isLoggerPresent(LoggingType loggingType) {
    return Optional.ofNullable(this.getLogging())
        .filter(logging -> Objects.nonNull(logging.getLoggingType()))
        .map(LoggingTemplate::getLoggingType)
        .filter(loggers -> loggers.containsKey(loggingType.getCode()))
        .map(loggers -> loggers.get(loggingType.getCode()))
        .orElse(Boolean.TRUE);
  }

  public ObfuscationTemplate searchObfuscation() {
    return Optional.ofNullable(this.getLogging())
        .filter(logging -> Objects.nonNull(logging.getObfuscation()))
        .map(LoggingTemplate::getObfuscation)
        .orElseGet(() -> {
          ObfuscationTemplate obfuscation = new ObfuscationTemplate();
          obfuscation.setBodyFields(Set.of());
          obfuscation.setHeaders(Set.of());
          return obfuscation;
        });
  }
}