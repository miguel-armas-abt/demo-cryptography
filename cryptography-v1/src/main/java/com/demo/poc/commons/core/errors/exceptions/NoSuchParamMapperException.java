package com.demo.poc.commons.core.errors.exceptions;

import com.demo.poc.commons.core.constants.Symbol;
import com.demo.poc.commons.custom.exceptions.ErrorDictionary;
import lombok.Getter;

@Getter
public class NoSuchParamMapperException extends GenericException {

  public NoSuchParamMapperException(Class<?> mapperClass) {
    super(ErrorDictionary.NO_SUCH_PARAM_MAPPER.getMessage() + Symbol.COLON_WITH_SPACE + mapperClass.getName(), ErrorDictionary.parse(NoSuchParamMapperException.class));
  }
}
