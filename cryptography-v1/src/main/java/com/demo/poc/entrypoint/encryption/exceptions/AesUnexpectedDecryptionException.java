package com.demo.poc.entrypoint.encryption.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class AesUnexpectedDecryptionException extends GenericException {

    private static final String EXCEPTION_CODE = "02.03.04";

    public AesUnexpectedDecryptionException(Throwable ex) {
        super(
            EXCEPTION_CODE,
            "Unexpected AES decryption error: " + ex.getMessage(),
            INTERNAL_SERVER_ERROR
        );
    }
}
