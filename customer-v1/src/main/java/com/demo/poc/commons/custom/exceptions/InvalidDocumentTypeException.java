package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;
import lombok.Getter;

@Getter
public class InvalidDocumentTypeException extends GenericException {

    public InvalidDocumentTypeException() {
        super(ErrorDictionary.INVALID_DOCUMENT_TYPE.getMessage(), ErrorDictionary.parse(InvalidDocumentTypeException.class));
    }
}
