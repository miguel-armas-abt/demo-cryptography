package com.demo.poc.commons.core.restclient.utils;

import java.util.Map;
import java.util.TreeMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.demo.poc.commons.core.restclient.utils.ParameterMapFiller.addProvidedParams;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FormDataFiller {

  public static Map<String, String> fillFormData(Map<String, String> providedParams,
                                                 Map<String, String> currentParams) {

    Map<String, String> params = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    params.putAll(currentParams);
    addProvidedParams(providedParams).accept(params);
    return params;
  }
}