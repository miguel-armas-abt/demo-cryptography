package com.demo.poc.commons.core.config;

import com.demo.poc.commons.core.interceptor.restclient.request.RestClientRequestInterceptor;
import com.demo.poc.commons.core.interceptor.restclient.response.RestClientResponseInterceptor;
import com.demo.poc.commons.core.interceptor.restserver.RestServerInterceptor;
import com.demo.poc.commons.core.logging.ThreadContextInjector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterceptorConfig {

  @Bean
  public RestClientRequestInterceptor restClientRequestInterceptor(ThreadContextInjector threadContextInjector) {
    return new RestClientRequestInterceptor(threadContextInjector);
  }

  @Bean
  public RestClientResponseInterceptor restClientResponseInterceptor(ThreadContextInjector threadContextInjector) {
    return new RestClientResponseInterceptor(threadContextInjector);
  }

  @Bean
  public RestServerInterceptor restServerInterceptor(ThreadContextInjector threadContextInjector) {
    return new RestServerInterceptor(threadContextInjector);
  }
}
