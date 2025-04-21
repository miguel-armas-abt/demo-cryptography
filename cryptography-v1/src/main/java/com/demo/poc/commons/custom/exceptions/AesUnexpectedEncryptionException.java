package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

public class AesUnexpectedEncryptionException extends GenericException {

    public AesUnexpectedEncryptionException(Throwable ex) {
        super(ex.getMessage(), ErrorDictionary.parse(AesUnexpectedEncryptionException.class));
    }
}
