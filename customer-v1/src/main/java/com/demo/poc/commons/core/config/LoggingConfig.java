package com.demo.poc.commons.core.config;

import com.demo.poc.commons.core.logging.ThreadContextInjector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {

  @Bean
  public ThreadContextInjector threadContextInjector() {
    return new ThreadContextInjector();
  }

}