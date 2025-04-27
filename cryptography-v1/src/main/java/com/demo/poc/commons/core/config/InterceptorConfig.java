package com.demo.poc.commons.core.config;

import com.demo.poc.commons.core.errors.selector.ResponseErrorSelector;
import com.demo.poc.commons.core.interceptor.error.ErrorInterceptor;
import com.demo.poc.commons.core.interceptor.restclient.request.RestClientRequestInterceptor;
import com.demo.poc.commons.core.interceptor.restclient.response.RestClientResponseInterceptor;
import com.demo.poc.commons.core.interceptor.restserver.RestServerInterceptor;
import com.demo.poc.commons.core.logging.ErrorThreadContextInjector;
import com.demo.poc.commons.core.logging.ThreadContextInjector;
import com.demo.poc.commons.custom.properties.ApplicationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;

@Configuration
public class InterceptorConfig {

  @Bean
  public ClientHttpRequestInterceptor restClientRequestInterceptor(ThreadContextInjector contextInjector,
                                                                   ApplicationProperties properties) {
    return new RestClientRequestInterceptor(contextInjector, properties);
  }

  @Bean
  public ClientHttpRequestInterceptor restClientResponseInterceptor(ThreadContextInjector contextInjector,
                                                                    ApplicationProperties properties) {
    return new RestClientResponseInterceptor(contextInjector, properties);
  }

  @Bean
  public RestServerInterceptor restServerInterceptor(ThreadContextInjector contextInjector,
                                                     ApplicationProperties properties) {
    return new RestServerInterceptor(contextInjector, properties);
  }

  @Bean
  public ErrorInterceptor errorInterceptor(ErrorThreadContextInjector contextInjector,
                                           ApplicationProperties properties,
                                           ResponseErrorSelector responseErrorSelector) {
    return new ErrorInterceptor(contextInjector, properties, responseErrorSelector);
  }

}