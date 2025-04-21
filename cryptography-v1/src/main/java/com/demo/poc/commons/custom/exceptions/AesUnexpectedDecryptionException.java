package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

public class AesUnexpectedDecryptionException extends GenericException {

    public AesUnexpectedDecryptionException(Throwable ex) {
        super(ex.getMessage(), ErrorDictionary.parse(AesUnexpectedDecryptionException.class));
    }
}
