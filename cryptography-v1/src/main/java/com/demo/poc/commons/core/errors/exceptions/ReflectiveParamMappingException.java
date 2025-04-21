package com.demo.poc.commons.core.errors.exceptions;

import com.demo.poc.commons.custom.exceptions.ErrorDictionary;
import lombok.Getter;

@Getter
public class ReflectiveParamMappingException extends GenericException {

  public ReflectiveParamMappingException(String message) {
    super(message, ErrorDictionary.parse(ReflectiveParamMappingException.class));
  }
}
