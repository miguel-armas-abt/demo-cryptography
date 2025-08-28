package com.demo.service.commons.config.di;

import com.demo.commons.errors.selector.ResponseErrorSelector;
import com.demo.commons.errors.selector.RestClientErrorSelector;
import com.demo.commons.properties.ConfigurationBaseProperties;
import com.demo.commons.restclient.error.RestClientErrorMapper;
import com.demo.commons.restclient.error.extractor.poc.DefaultErrorExtractor;
import com.demo.commons.serialization.JsonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorDIConfig {

  @Bean
  public DefaultErrorExtractor defaultErrorExtractor(RestClientErrorMapper errorMapper) {
    return new DefaultErrorExtractor(errorMapper);
  }

  @Bean
  public RestClientErrorMapper restClientErrorMapper(JsonSerializer jsonSerializer) {
    return new RestClientErrorMapper(jsonSerializer);
  }

  @Bean
  public ResponseErrorSelector responseErrorSelector(ConfigurationBaseProperties properties) {
    return new ResponseErrorSelector(properties);
  }

  @Bean
  public RestClientErrorSelector restClientErrorSelector(ConfigurationBaseProperties properties) {
    return new RestClientErrorSelector(properties);
  }
}
