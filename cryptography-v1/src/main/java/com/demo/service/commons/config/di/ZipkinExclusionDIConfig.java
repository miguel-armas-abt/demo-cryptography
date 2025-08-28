package com.demo.service.commons.config.di;

import io.micrometer.tracing.exporter.SpanExportingPredicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class ZipkinExclusionDIConfig {

  private static final String URI = "uri";
  private static final String ACTUATOR_PATH = "/actuator";

  @Bean
  public SpanExportingPredicate noActuator() {
    return span -> Objects.isNull(span.getTags().get(URI)) || !span.getTags().get(URI).startsWith(ACTUATOR_PATH);
  }
}
