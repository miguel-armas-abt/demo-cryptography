package com.demo.poc.commons.core.config;

import com.demo.poc.commons.core.logging.ErrorThreadContextInjector;
import com.demo.poc.commons.core.logging.RestClientThreadContextInjector;
import com.demo.poc.commons.core.logging.RestServerThreadContextInjector;
import com.demo.poc.commons.core.logging.ThreadContextInjector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {

  @Bean
  public ThreadContextInjector threadContextInjector() {
    return new ThreadContextInjector();
  }

  @Bean
  public ErrorThreadContextInjector threadContextErrorInjector(ThreadContextInjector context) {
    return new ErrorThreadContextInjector(context);
  }

  @Bean
  public RestClientThreadContextInjector threadContextRestClientInjector(ThreadContextInjector context) {
    return new RestClientThreadContextInjector(context);
  }

  @Bean
  public RestServerThreadContextInjector threadContextRestServerInjector(ThreadContextInjector context) {
    return new RestServerThreadContextInjector(context);
  }
}