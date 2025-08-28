package com.demo.service.entrypoint.encryption.exceptions;

import com.demo.commons.errors.exceptions.GenericException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class NoSuchEncryptionStrategyException extends GenericException {

  private static final String EXCEPTION_CODE = "02.03.13";

  public NoSuchEncryptionStrategyException() {
    super(
        EXCEPTION_CODE,
        "No such encryption strategy",
        INTERNAL_SERVER_ERROR
    );
  }
}
