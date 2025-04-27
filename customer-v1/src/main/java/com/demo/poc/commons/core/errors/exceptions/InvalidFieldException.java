package com.demo.poc.commons.core.errors.exceptions;

import com.demo.poc.commons.core.constants.Symbol;
import com.demo.poc.commons.custom.exceptions.ErrorDictionary;
import lombok.Getter;

@Getter
public class InvalidFieldException extends GenericException {

    public InvalidFieldException(String message) {
        super(ErrorDictionary.INVALID_FIELD.getMessage() + Symbol.COLON_WITH_SPACE + message, ErrorDictionary.parse(InvalidFieldException.class));
    }
}
