package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

public class RsaNoSuchPrivateKeyException extends GenericException {

  public RsaNoSuchPrivateKeyException() {
    super(ErrorDictionary.RSA_NO_SUCH_PRIVATE_KEY.getMessage(), ErrorDictionary.parse(RsaNoSuchPrivateKeyException.class));
  }
}
