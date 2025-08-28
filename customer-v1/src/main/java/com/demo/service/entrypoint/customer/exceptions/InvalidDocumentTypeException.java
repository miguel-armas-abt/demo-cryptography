package com.demo.service.entrypoint.customer.exceptions;

import com.demo.commons.errors.exceptions.GenericException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InvalidDocumentTypeException extends GenericException {

    private static final String EXCEPTION_CODE = "01.01.03";

    public InvalidDocumentTypeException() {
        super(
            EXCEPTION_CODE,
            "The document type is not defined",
            BAD_REQUEST
        );
    }
}
