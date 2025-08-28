package com.demo.service.entrypoint.encryption.exceptions;

import com.demo.commons.errors.exceptions.GenericException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class RsaNoSuchPublicKeyException extends GenericException {

  private static final String EXCEPTION_CODE = "02.03.12";

  public RsaNoSuchPublicKeyException() {
    super(
        EXCEPTION_CODE,
        "No such RSA public key",
        INTERNAL_SERVER_ERROR);
  }
}
