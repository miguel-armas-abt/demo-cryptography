package com.demo.poc.commons.core.restclient.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.demo.poc.commons.core.properties.restclient.HeaderTemplate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.demo.poc.commons.core.restclient.utils.ParameterMapFiller.addForwardedParams;
import static com.demo.poc.commons.core.restclient.utils.ParameterMapFiller.addGeneratedParams;
import static com.demo.poc.commons.core.restclient.utils.ParameterMapFiller.addProvidedParams;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeadersFiller {

  public static Map<String, String> fillHeaders(HeaderTemplate headerTemplate,
                                                Map<String, String> currentHeaders) {

    Consumer<Map<String, String>> providedHeaders = addProvidedParams(headerTemplate.getProvided());
    Consumer<Map<String, String>> generatedHeaders = addGeneratedParams(headerTemplate.getGenerated());
    Consumer<Map<String, String>> forwardedHeaders = addForwardedParams(headerTemplate.getForwarded(), currentHeaders);

    Map<String, String> headers = new HashMap<>();
    providedHeaders.accept(headers);
    generatedHeaders.accept(headers);
    forwardedHeaders.accept(headers);

    return headers;
  }
}