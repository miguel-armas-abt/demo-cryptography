package com.demo.poc.commons.core.errors.exceptions;

import com.demo.poc.commons.custom.exceptions.ErrorDictionary;
import lombok.Getter;

@Getter
public class JsonReadException extends GenericException {

  public JsonReadException(String message) {
    super(message, ErrorDictionary.parse(JsonReadException.class));
  }
}
