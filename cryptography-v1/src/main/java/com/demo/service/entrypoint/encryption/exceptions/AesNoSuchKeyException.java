package com.demo.service.entrypoint.encryption.exceptions;

import com.demo.commons.errors.exceptions.GenericException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class AesNoSuchKeyException extends GenericException {

  private static final String EXCEPTION_CODE = "02.03.02";

  public AesNoSuchKeyException() {
    super(
        EXCEPTION_CODE,
        "No such AES key",
        INTERNAL_SERVER_ERROR
    );
  }
}
