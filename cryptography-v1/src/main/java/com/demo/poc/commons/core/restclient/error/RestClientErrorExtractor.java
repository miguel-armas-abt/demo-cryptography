package com.demo.poc.commons.core.restclient.error;

import java.util.Map;
import java.util.Optional;

public interface RestClientErrorExtractor {

  Optional<Map.Entry<String, String>> getCodeAndMessage(String jsonBody);

  boolean supports(Class<?> wrapperClass);
}