package com.demo.service.entrypoint.encryption.exceptions;

import com.demo.commons.errors.exceptions.GenericException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class RsaUnexpectedDecryptionException extends GenericException {

    private static final String EXCEPTION_CODE = "02.03.09";

    public RsaUnexpectedDecryptionException(Throwable ex) {
        super(
            EXCEPTION_CODE,
            "Unexpected RSA decryption error: " + ex.getMessage(),
            INTERNAL_SERVER_ERROR
        );
    }
}
