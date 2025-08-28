package com.demo.service.entrypoint.encryption.exceptions;

import com.demo.commons.errors.exceptions.GenericException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class AesInvalidIVLengthException extends GenericException {

  private static final String EXCEPTION_CODE = "02.03.05";

  public AesInvalidIVLengthException() {
    super(
        EXCEPTION_CODE,
        "Invalid IV length",
        INTERNAL_SERVER_ERROR
    );
  }
}
