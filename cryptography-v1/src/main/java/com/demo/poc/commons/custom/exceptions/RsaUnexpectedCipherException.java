package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

public class RsaUnexpectedCipherException extends GenericException {

    public RsaUnexpectedCipherException(Throwable ex) {
        super(ex.getMessage(), ErrorDictionary.parse(RsaUnexpectedCipherException.class));
    }
}
