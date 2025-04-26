package com.demo.poc.commons.core.config;

import com.demo.poc.commons.core.interceptor.restclient.request.RestClientRequestInterceptor;
import com.demo.poc.commons.core.interceptor.restclient.response.RestClientResponseInterceptor;
import com.demo.poc.commons.core.interceptor.restserver.RestServerInterceptor;
import com.demo.poc.commons.core.logging.RestClientThreadContextInjector;
import com.demo.poc.commons.core.logging.RestServerThreadContextInjector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterceptorConfig {

  @Bean
  public RestClientRequestInterceptor restClientRequestInterceptor(RestClientThreadContextInjector context) {
    return new RestClientRequestInterceptor(context);
  }

  @Bean
  public RestClientResponseInterceptor restClientResponseInterceptor(RestClientThreadContextInjector context) {
    return new RestClientResponseInterceptor(context);
  }

  @Bean
  public RestServerInterceptor restServerInterceptor(RestServerThreadContextInjector context) {
    return new RestServerInterceptor(context);
  }
}
