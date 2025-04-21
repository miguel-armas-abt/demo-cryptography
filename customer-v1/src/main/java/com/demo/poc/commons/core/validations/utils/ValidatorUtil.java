package com.demo.poc.commons.core.validations.utils;

import java.util.Set;
import java.util.stream.Collectors;

import com.demo.poc.commons.core.errors.exceptions.InvalidFieldException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidatorUtil {

  public static <T> void handleValidationErrors(@NotNull Set<ConstraintViolation<T>> violations) {
    if (!violations.isEmpty()) {
      String errorMessages = violations.stream()
          .map(violation -> String.format("The field '%s' %s",
              violation.getPropertyPath(), violation.getMessage()))
          .collect(Collectors.joining("; "));
      throw new InvalidFieldException(errorMessages);
    }
  }
}