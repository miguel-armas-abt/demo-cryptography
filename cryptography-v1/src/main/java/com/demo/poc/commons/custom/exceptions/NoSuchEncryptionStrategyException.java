package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

public class NoSuchEncryptionStrategyException extends GenericException {

  public NoSuchEncryptionStrategyException() {
    super(ErrorDictionary.NO_SUCH_ENCRYPTION_STRATEGY.getMessage(), ErrorDictionary.parse(NoSuchEncryptionStrategyException.class));
  }
}
