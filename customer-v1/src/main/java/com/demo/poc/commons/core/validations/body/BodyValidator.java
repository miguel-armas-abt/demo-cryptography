package com.demo.poc.commons.core.validations.body;

import java.util.Set;

import com.demo.poc.commons.core.validations.utils.ValidatorUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BodyValidator {

  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  public <T> void validate(T body) {
    Set<ConstraintViolation<T>> violations = validator.validate(body);
    ValidatorUtil.handleValidationErrors(violations);
  }

  public <T> boolean isValid(T body) {
    return validator.validate(body).isEmpty();
  }
}