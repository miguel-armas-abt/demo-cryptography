package com.demo.poc.commons.core.validations;

import com.demo.poc.commons.core.errors.exceptions.NoSuchParamMapperException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ParamValidator {

  private final List<ParamMapper> paramMappers;
  private final BodyValidator bodyValidator;

  public <T> T validateAndGet(Map<String, String> paramsMap, Class<T> paramClass) {
    T params = (T) selectMapper(paramClass).map(paramsMap);
    bodyValidator.validate(params);
    return params;
  }

  public <T> void validate(Map<String, String> params, Class<T> paramClass) {
    validateAndGet(params, paramClass);
  }

  private ParamMapper selectMapper(Class<?> paramClass) {
    return paramMappers.stream()
        .filter(mapper -> mapper.supports(paramClass))
        .findFirst()
        .orElseThrow(() -> new NoSuchParamMapperException(paramClass));
  }
}