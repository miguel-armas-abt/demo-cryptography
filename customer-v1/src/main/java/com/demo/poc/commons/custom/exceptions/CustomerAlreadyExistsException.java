package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

public class CustomerAlreadyExistsException extends GenericException {

  public CustomerAlreadyExistsException() {
    super(ErrorDictionary.CUSTOMER_ALREADY_EXISTS.getMessage(), ErrorDictionary.parse(CustomerAlreadyExistsException.class));
  }
}
