package com.demo.service.commons.config.di;

import com.demo.commons.errors.selector.RestClientErrorSelector;
import com.demo.commons.restclient.RestClientTemplate;
import com.demo.commons.restclient.error.RestClientErrorExtractor;
import com.demo.commons.restclient.error.RestClientErrorHandler;
import com.demo.service.commons.properties.ApplicationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class RestClientDIConfig {

  @Bean
  public RestTemplate restTemplate(List<ClientHttpRequestInterceptor> requestInterceptors) {
    RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    restTemplate.setInterceptors(requestInterceptors);
    return restTemplate;
  }

  @Bean
  public RestClientErrorHandler restClientErrorHandler(List<RestClientErrorExtractor> restClientErrorExtractors,
                                                       RestClientErrorSelector restClientErrorSelector) {
    return new RestClientErrorHandler(restClientErrorExtractors, restClientErrorSelector);
  }

  @Bean
  public RestClientTemplate customRestTemplate(RestTemplate restTemplate,
                                               ApplicationProperties properties,
                                               RestClientErrorHandler restClientErrorHandler) {
    return new RestClientTemplate(restTemplate, properties, restClientErrorHandler);
  }
}