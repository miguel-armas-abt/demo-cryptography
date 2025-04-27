package com.demo.poc.commons.core.errors.exceptions;

import com.demo.poc.commons.core.constants.Symbol;
import com.demo.poc.commons.custom.exceptions.ErrorDictionary;
import lombok.Getter;

@Getter
public class NoSuchRestClientErrorExtractorException extends GenericException {

  public NoSuchRestClientErrorExtractorException(Class<?> errorWrapperClass) {
    super(ErrorDictionary.NO_SUCH_REST_CLIENT_ERROR_EXTRACTOR.getMessage() + Symbol.COLON_WITH_SPACE + errorWrapperClass.getName(), ErrorDictionary.parse(NoSuchRestClientErrorExtractorException.class));
  }
}
