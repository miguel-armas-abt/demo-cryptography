package com.demo.poc.commons.custom.properties;

import com.demo.poc.commons.core.properties.ConfigurationBaseProperties;
import com.demo.poc.commons.custom.properties.crypto.CryptographyTemplate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "configuration")
public class ApplicationProperties extends ConfigurationBaseProperties {

  private Map<String, CryptographyTemplate> cryptography;

}
