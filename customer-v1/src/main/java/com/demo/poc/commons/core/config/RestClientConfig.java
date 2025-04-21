package com.demo.poc.commons.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import com.demo.poc.commons.core.restclient.RestClientTemplate;
import com.demo.poc.commons.custom.properties.ApplicationProperties;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClientTemplate restClientTemplate(RestTemplate restTemplate,
                                                 ApplicationProperties properties) {
        return new RestClientTemplate(restTemplate, properties);
    }

    @Bean
    public RestTemplate restTemplate(List<ClientHttpRequestInterceptor> requestInterceptors) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(requestInterceptors);
        return restTemplate;
    }
}
