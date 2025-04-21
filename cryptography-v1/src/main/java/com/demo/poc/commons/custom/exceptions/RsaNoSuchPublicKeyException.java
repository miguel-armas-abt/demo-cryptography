package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

public class RsaNoSuchPublicKeyException extends GenericException {

  public RsaNoSuchPublicKeyException() {
    super(ErrorDictionary.RSA_NO_SUCH_PUBLIC_KEY.getMessage(), ErrorDictionary.parse(RsaNoSuchPublicKeyException.class));
  }
}
