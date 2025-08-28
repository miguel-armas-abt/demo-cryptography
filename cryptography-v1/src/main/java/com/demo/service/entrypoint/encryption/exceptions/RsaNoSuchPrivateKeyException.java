package com.demo.service.entrypoint.encryption.exceptions;

import com.demo.commons.errors.exceptions.GenericException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class RsaNoSuchPrivateKeyException extends GenericException {

  private static final String EXCEPTION_CODE = "02.03.11";

  public RsaNoSuchPrivateKeyException() {
    super(
        EXCEPTION_CODE,
        "No such RSA private key",
        INTERNAL_SERVER_ERROR
    );
  }
}
