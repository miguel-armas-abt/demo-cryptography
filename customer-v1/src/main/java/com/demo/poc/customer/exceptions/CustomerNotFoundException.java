package com.demo.poc.customer.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class CustomerNotFoundException extends GenericException {

    private static final String EXCEPTION_CODE = "01.01.02";

    public CustomerNotFoundException() {
        super(
            EXCEPTION_CODE,
            "The customer doesn't exist",
            NOT_FOUND
        );
    }
}
