package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

public class RsaUnexpectedDecryptionException extends GenericException {

    public RsaUnexpectedDecryptionException(Throwable ex) {
        super(ex.getMessage(), ErrorDictionary.parse(RsaUnexpectedDecryptionException.class));
    }
}
