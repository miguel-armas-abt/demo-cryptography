package com.demo.service.entrypoint.encryption.exceptions;

import com.demo.commons.errors.exceptions.GenericException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class RsaUnexpectedPrivateKeyReadingException extends GenericException {

    private static final String EXCEPTION_CODE = "02.03.07";

    public RsaUnexpectedPrivateKeyReadingException(Throwable ex) {
        super(
            EXCEPTION_CODE,
            "Unexpected RSA private key reading error: " + ex.getMessage(),
            INTERNAL_SERVER_ERROR
        );
    }
}
