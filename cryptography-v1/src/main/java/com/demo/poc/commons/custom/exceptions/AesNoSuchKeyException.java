package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

public class AesNoSuchKeyException extends GenericException {

  public AesNoSuchKeyException() {
    super(ErrorDictionary.AES_NO_SUCH_KEY.getMessage(), ErrorDictionary.parse(AesNoSuchKeyException.class));
  }
}
