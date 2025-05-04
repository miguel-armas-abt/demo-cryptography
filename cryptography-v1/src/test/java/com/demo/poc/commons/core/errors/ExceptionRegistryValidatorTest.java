package com.demo.poc.commons.core.errors;

import com.demo.poc.commons.core.errors.exceptions.GenericException;
import com.demo.poc.commons.core.errors.exceptions.RestClientException;
import com.demo.poc.commons.custom.exceptions.ErrorDictionary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ExceptionRegistryValidatorTest {

  private static final String COMMONS_PACKAGE = "com.demo.poc.commons";

  @Test
  @DisplayName("When an exception isn't registered in ErrorDictionary, then throw exception")
  void whenExceptionIsNotRegisteredInErrorDictionary_thenThrowException() {
    assertDoesNotThrow(this::validateExceptionRegistration);
  }

  private void validateExceptionRegistration() {
    ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
    scanner.addIncludeFilter(new AssignableTypeFilter(GenericException.class));

    Set<Class<? extends GenericException>> registeredExceptions = Arrays.stream(ErrorDictionary.values())
        .map(ErrorDictionary::getExceptionClass)
        .collect(Collectors.toSet());

    scanner.findCandidateComponents(COMMONS_PACKAGE)
        .stream()
        .map(bean -> {
          try {
            return Class.forName(bean.getBeanClassName());
          } catch (ClassNotFoundException exception) {
            throw new RuntimeException("Class not found: " + bean.getBeanClassName(), exception);
          }
        })
        .filter(cls -> !cls.equals(RestClientException.class))
        .filter(cls -> !cls.equals(GenericException.class))
        .filter(cls -> !registeredExceptions.contains(cls))
        .findFirst()
        .ifPresent(cls -> {
          throw new IllegalStateException("No registered exception in ErrorDictionary: " + cls.getName() + ". You must use or delete it.");
        });
  }
}