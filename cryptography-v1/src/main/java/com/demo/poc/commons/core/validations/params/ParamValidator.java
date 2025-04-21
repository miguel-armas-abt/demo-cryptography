package com.demo.poc.commons.core.validations.params;

import java.util.Map;

import com.demo.poc.commons.core.validations.body.BodyValidator;
import com.demo.poc.commons.core.validations.utils.ParamReflectiveMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ParamValidator {

  private final BodyValidator bodyValidator;

  public <T extends DefaultParams> T validateAndRetrieve(Map<String, String> params, Class<T> paramClass) {
    T parameters = ParamReflectiveMapper.mapParam(params, paramClass, false);
    bodyValidator.validate(parameters);
    return parameters;
  }

  public <T extends DefaultParams> void validate(Map<String, String> params, Class<T> paramClass) {
    validateAndRetrieve(params, paramClass);
  }
}