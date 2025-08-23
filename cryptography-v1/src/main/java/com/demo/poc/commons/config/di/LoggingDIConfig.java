package com.demo.poc.commons.config.di;

import com.demo.poc.commons.core.logging.ErrorThreadContextInjector;
import com.demo.poc.commons.core.logging.ThreadContextInjector;
import com.demo.poc.commons.properties.ApplicationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingDIConfig {

  @Bean
  public ThreadContextInjector threadContextInjector(ApplicationProperties properties) {
    return new ThreadContextInjector(properties);
  }

  @Bean
  public ErrorThreadContextInjector errorThreadContextInjector(ThreadContextInjector contextInjector) {
    return new ErrorThreadContextInjector(contextInjector);
  }
}