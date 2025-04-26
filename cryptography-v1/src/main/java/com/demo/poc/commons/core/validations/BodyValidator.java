package com.demo.poc.commons.core.validations;

import com.demo.poc.commons.core.constants.Symbol;
import com.demo.poc.commons.core.errors.exceptions.InvalidFieldException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BodyValidator {

  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  public <T> void validate(T body) {
    Set<ConstraintViolation<T>> violations = validator.validate(body);
    handleValidationErrors(violations);
  }

  public <T> boolean isValid(T body) {
    return validator.validate(body).isEmpty();
  }

  private static <T> void handleValidationErrors(Set<ConstraintViolation<T>> violations) {
    if (!violations.isEmpty()) {
      String errorMessages = violations.stream()
          .map(violation -> String.format("The field '%s' %s",
              violation.getPropertyPath(), violation.getMessage()))
          .collect(Collectors.joining(Symbol.COMMA));
      throw new InvalidFieldException(errorMessages);
    }
  }
}