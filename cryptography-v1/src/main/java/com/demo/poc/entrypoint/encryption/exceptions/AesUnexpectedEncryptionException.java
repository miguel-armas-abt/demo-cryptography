package com.demo.poc.entrypoint.encryption.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class AesUnexpectedEncryptionException extends GenericException {

    private static final String EXCEPTION_CODE = "02.03.03";

    public AesUnexpectedEncryptionException(Throwable ex) {
        super(
            EXCEPTION_CODE,
            "Unexpected AES encryption error: " + ex.getMessage(),
            INTERNAL_SERVER_ERROR
        );
    }
}
