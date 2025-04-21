package com.demo.poc.commons.core.validations.headers;

import java.util.Map;

import com.demo.poc.commons.core.validations.body.BodyValidator;
import com.demo.poc.commons.core.validations.utils.ParamReflectiveMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HeaderValidator {

  private final BodyValidator bodyValidator;

  public <T extends DefaultHeaders> T validateAndRetrieve(Map<String, String> headers,
                                                          Class<T> headerClass) {
    T parameters = ParamReflectiveMapper.mapParam(headers, headerClass, true);
    bodyValidator.validate(parameters);
    return parameters;
  }

  public <T extends DefaultHeaders> void validate(Map<String, String> headers, Class<T> headerClass) {
    validateAndRetrieve(headers, headerClass);
  }
}