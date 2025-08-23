package com.demo.poc.entrypoint.encryption.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class NoSuchCryptographyTemplateException extends GenericException {

  private static final String EXCEPTION_CODE = "02.03.14";

  public NoSuchCryptographyTemplateException() {
    super(
        EXCEPTION_CODE,
        "No such cryptography template by feature",
        INTERNAL_SERVER_ERROR
    );
  }
}
