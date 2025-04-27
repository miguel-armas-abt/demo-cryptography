package com.demo.poc.commons.core.errors.exceptions;

import com.demo.poc.commons.core.constants.Symbol;
import com.demo.poc.commons.custom.exceptions.ErrorDictionary;
import lombok.Getter;

@Getter
public class NoSuchRestClientException extends GenericException {

  public NoSuchRestClientException(String serviceName) {
    super(ErrorDictionary.NO_SUCH_REST_CLIENT.getMessage() + Symbol.COLON_WITH_SPACE + serviceName, ErrorDictionary.parse(NoSuchRestClientException.class));
  }
}