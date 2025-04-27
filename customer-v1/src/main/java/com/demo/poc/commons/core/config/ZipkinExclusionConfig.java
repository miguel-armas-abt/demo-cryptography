package com.demo.poc.commons.core.config;

import java.util.Objects;

import io.micrometer.tracing.exporter.SpanExportingPredicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZipkinExclusionConfig {

  private static final String URI = "uri";
  private static final String ACTUATOR_PATH = "/actuator";

  @Bean
  public SpanExportingPredicate noActuator() {
    return span -> Objects.isNull(span.getTags().get(URI)) || !span.getTags().get(URI).startsWith(ACTUATOR_PATH);
  }
}
