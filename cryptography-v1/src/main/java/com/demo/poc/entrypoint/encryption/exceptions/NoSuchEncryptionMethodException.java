package com.demo.poc.entrypoint.encryption.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class NoSuchEncryptionMethodException extends GenericException {

  private static final String EXCEPTION_CODE = "02.03.01";

  public NoSuchEncryptionMethodException() {
    super(
        EXCEPTION_CODE,
        "No such encryption method",
        INTERNAL_SERVER_ERROR
    );
  }
}
