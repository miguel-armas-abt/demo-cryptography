package com.demo.poc.entrypoint.customer.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class CustomerAlreadyExistsException extends GenericException {

  private static final String EXCEPTION_CODE = "01.01.04";

  public CustomerAlreadyExistsException() {
    super(
        EXCEPTION_CODE,
        "The customer already exists",
        BAD_REQUEST
    );
  }
}
