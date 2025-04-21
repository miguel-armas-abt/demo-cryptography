package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

public class RsaUnexpectedEncryptionException extends GenericException {

    public RsaUnexpectedEncryptionException(Throwable ex) {
        super(ex.getMessage(), ErrorDictionary.parse(RsaUnexpectedEncryptionException.class));
    }
}
