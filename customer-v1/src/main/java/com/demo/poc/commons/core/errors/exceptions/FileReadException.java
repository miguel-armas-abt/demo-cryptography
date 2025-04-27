package com.demo.poc.commons.core.errors.exceptions;

import com.demo.poc.commons.custom.exceptions.ErrorDictionary;
import lombok.Getter;

@Getter
public class FileReadException extends GenericException {

  public FileReadException(String message) {
    super(message, ErrorDictionary.parse(FileReadException.class));
  }
}
