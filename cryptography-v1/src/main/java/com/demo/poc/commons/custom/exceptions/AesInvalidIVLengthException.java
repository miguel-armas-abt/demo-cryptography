package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

public class AesInvalidIVLengthException extends GenericException {

  public AesInvalidIVLengthException() {
    super(ErrorDictionary.AES_INVALID_IV_LENGTH.getMessage(), ErrorDictionary.parse(AesInvalidIVLengthException.class));
  }
}
