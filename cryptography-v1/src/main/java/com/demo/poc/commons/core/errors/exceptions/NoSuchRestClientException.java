package com.demo.poc.commons.core.errors.exceptions;

import com.demo.poc.commons.custom.exceptions.ErrorDictionary;
import lombok.Getter;

@Getter
public class NoSuchRestClientException extends GenericException {

    public NoSuchRestClientException() {
        super(ErrorDictionary.NO_SUCH_REST_CLIENT.getMessage(), ErrorDictionary.parse(NoSuchRestClientException.class));
    }
}
