package com.demo.service.commons.config.di;

import com.demo.commons.errors.selector.ResponseErrorSelector;
import com.demo.commons.interceptor.error.ErrorInterceptor;
import com.demo.commons.interceptor.restclient.request.RestClientRequestInterceptor;
import com.demo.commons.interceptor.restclient.response.RestClientResponseInterceptor;
import com.demo.commons.interceptor.restserver.RestServerInterceptor;
import com.demo.commons.logging.ErrorThreadContextInjector;
import com.demo.commons.logging.ThreadContextInjector;
import com.demo.service.commons.properties.ApplicationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;

@Configuration
public class InterceptorDIConfig {

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