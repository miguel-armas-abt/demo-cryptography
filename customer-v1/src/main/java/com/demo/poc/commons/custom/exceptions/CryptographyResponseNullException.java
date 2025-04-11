package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

public class CryptographyResponseNullException extends GenericException {

    public CryptographyResponseNullException() {
        super(ErrorDictionary.CRYPTOGRAPHY_RESPONSE_NULL.getMessage(), ErrorDictionary.parse(CryptographyResponseNullException.class));
    }
}
