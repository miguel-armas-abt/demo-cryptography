package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

import static com.demo.poc.commons.custom.exceptions.ErrorDictionary.parse;

public class RsaUnexpectedPrivateKeyReadingException extends GenericException {

    public RsaUnexpectedPrivateKeyReadingException(Throwable ex) {
        super(ex.getMessage(), parse(RsaUnexpectedPrivateKeyReadingException.class));
    }
}
