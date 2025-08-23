package com.demo.poc.entrypoint.encryption.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class RsaUnexpectedCipherException extends GenericException {

    private static final String EXCEPTION_CODE = "02.03.10";

    public RsaUnexpectedCipherException(Throwable ex) {
        super(
            EXCEPTION_CODE,
            "Unexpected Cipher creation error: " + ex.getMessage(),
            INTERNAL_SERVER_ERROR
        );
    }
}
