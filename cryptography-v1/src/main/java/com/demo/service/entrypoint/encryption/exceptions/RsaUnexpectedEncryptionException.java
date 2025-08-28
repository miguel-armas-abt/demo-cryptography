package com.demo.service.entrypoint.encryption.exceptions;

import com.demo.commons.errors.exceptions.GenericException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class RsaUnexpectedEncryptionException extends GenericException {

    private static final String EXCEPTION_CODE = "02.03.09";

    public RsaUnexpectedEncryptionException(Throwable ex) {
        super(
            EXCEPTION_CODE,
            "Unexpected RSA encryption error: " + ex.getMessage(),
            INTERNAL_SERVER_ERROR
        );
    }
}
