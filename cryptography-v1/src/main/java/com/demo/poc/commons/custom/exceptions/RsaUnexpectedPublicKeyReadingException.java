package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

public class RsaUnexpectedPublicKeyReadingException extends GenericException {

    public RsaUnexpectedPublicKeyReadingException(Throwable ex) {
        super(ex.getMessage(), ErrorDictionary.parse(RsaUnexpectedPublicKeyReadingException.class));
    }
}
