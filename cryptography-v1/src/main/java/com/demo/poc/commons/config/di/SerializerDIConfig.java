package com.demo.poc.commons.config.di;

import com.demo.poc.commons.core.serialization.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SerializerDIConfig {

  @Bean
  public JsonSerializer jsonSerializer(ObjectMapper objectMapper) {
    return new JsonSerializer(objectMapper);
  }

}
