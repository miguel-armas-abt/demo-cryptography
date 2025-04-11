package com.demo.poc.commons.core.errors.exceptions;

import com.demo.poc.commons.core.errors.dto.ErrorDto;
import com.demo.poc.commons.custom.exceptions.ErrorDictionary;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GenericException extends RuntimeException {

    protected ErrorDto errorDetail;
    protected HttpStatus httpStatus;

    public GenericException(String message) {
        super(message);
    }

    public GenericException(String message, ErrorDictionary detail) {
        super(message);
        this.httpStatus = detail.getHttpStatus();
        this.errorDetail = ErrorDto.builder()
                .code(detail.getCode())
                .message(detail.getMessage())
                .build();
    }
}
