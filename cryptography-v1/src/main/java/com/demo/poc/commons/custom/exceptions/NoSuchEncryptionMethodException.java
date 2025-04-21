package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

public class NoSuchEncryptionMethodException extends GenericException {

  public NoSuchEncryptionMethodException() {
    super(ErrorDictionary.NO_SUCH_ENCRYPTION_METHOD.getMessage(), ErrorDictionary.parse(NoSuchEncryptionMethodException.class));
  }
}
