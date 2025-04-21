package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.exceptions.GenericException;
import com.demo.poc.commons.core.errors.exceptions.InvalidFieldException;
import com.demo.poc.commons.core.errors.exceptions.NoSuchCacheConfigException;
import com.demo.poc.commons.core.errors.exceptions.NoSuchRestClientException;
import com.demo.poc.commons.core.errors.exceptions.ReflectiveParamAssignmentException;
import com.demo.poc.commons.core.errors.exceptions.ReflectiveParamMappingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum ErrorDictionary {

    //system=00
    INVALID_FIELD("01.00.01", "Invalid field", BAD_REQUEST, InvalidFieldException.class),
    NO_SUCH_REST_CLIENT("01.00.02", "No such rest client", INTERNAL_SERVER_ERROR, NoSuchRestClientException.class),
    NO_SUCH_CACHE_CONFIG("01.00.03", "No such cache config", INTERNAL_SERVER_ERROR, NoSuchCacheConfigException.class),
    ERROR_MAPPING_REFLECTIVE_PARAMS("01.00.04", "Error mapping reflective params", INTERNAL_SERVER_ERROR, ReflectiveParamMappingException.class),
    ERROR_ASSIGN_REFLECTIVE_PARAMS("01.00.05", "Error assign reflective params", INTERNAL_SERVER_ERROR, ReflectiveParamAssignmentException.class),

    //custom=01
    CUSTOMER_NOT_FOUND("01.01.01", "The customer doesn't exist", NOT_FOUND, CustomerNotFoundException.class),
    INVALID_DOCUMENT_TYPE("01.01.02", "The document type is not defined", BAD_REQUEST, InvalidDocumentTypeException.class),
    CUSTOMER_ALREADY_EXISTS("01.01.03", "The customer already exists", BAD_REQUEST, CustomerAlreadyExistsException.class),
    CRYPTOGRAPHY_RESPONSE_NULL("01.01.04", "Cryptography response is null", CONFLICT, CryptographyResponseNullException.class);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
    private final Class<? extends GenericException> exceptionClass;

    public static ErrorDictionary parse(Class<? extends GenericException> exceptionClass) {
        return Arrays.stream(ErrorDictionary.values())
                .filter(errorDetail -> errorDetail.getExceptionClass().isAssignableFrom(exceptionClass))
                .findFirst()
                .orElseThrow(() -> new GenericException("No such exception"));
    }
}
