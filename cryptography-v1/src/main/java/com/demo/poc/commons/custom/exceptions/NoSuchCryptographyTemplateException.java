package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

public class NoSuchCryptographyTemplateException extends GenericException {

  public NoSuchCryptographyTemplateException() {
    super(ErrorDictionary.NO_SUCH_CRYPTOGRAPHY_TEMPLATE.getMessage(), ErrorDictionary.parse(NoSuchCryptographyTemplateException.class));
  }
}
