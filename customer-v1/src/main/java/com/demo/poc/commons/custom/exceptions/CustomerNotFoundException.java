package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;
import lombok.Getter;

@Getter
public class CustomerNotFoundException extends GenericException {

    public CustomerNotFoundException() {
        super(ErrorDictionary.CUSTOMER_NOT_FOUND.getMessage(), ErrorDictionary.parse(CustomerNotFoundException.class));
    }
}
