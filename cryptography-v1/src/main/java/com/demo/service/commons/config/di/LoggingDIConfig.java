package com.demo.service.commons.config.di;

import com.demo.commons.logging.ErrorThreadContextInjector;
import com.demo.commons.logging.ThreadContextInjector;
import com.demo.service.commons.properties.ApplicationProperties;
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