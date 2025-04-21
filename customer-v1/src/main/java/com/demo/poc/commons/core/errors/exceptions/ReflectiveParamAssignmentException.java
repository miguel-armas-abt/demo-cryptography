package com.demo.poc.commons.core.errors.exceptions;

import com.demo.poc.commons.custom.exceptions.ErrorDictionary;
import lombok.Getter;

@Getter
public class ReflectiveParamAssignmentException extends GenericException {

  public ReflectiveParamAssignmentException(String message) {
    super(message, ErrorDictionary.parse(ReflectiveParamAssignmentException.class));
  }
}
