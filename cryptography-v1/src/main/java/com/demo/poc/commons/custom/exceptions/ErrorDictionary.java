package com.demo.poc.commons.custom.exceptions;

import com.demo.poc.commons.core.errors.dto.ErrorType;
import com.demo.poc.commons.core.errors.exceptions.FileReadException;
import com.demo.poc.commons.core.errors.exceptions.GenericException;
import com.demo.poc.commons.core.errors.exceptions.InvalidFieldException;
import com.demo.poc.commons.core.errors.exceptions.JsonReadException;
import com.demo.poc.commons.core.errors.exceptions.NoSuchParamMapperException;
import com.demo.poc.commons.core.errors.exceptions.NoSuchRestClientErrorExtractorException;
import com.demo.poc.commons.core.errors.exceptions.NoSuchRestClientException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static com.demo.poc.commons.core.errors.dto.ErrorType.BUSINESS;
import static com.demo.poc.commons.core.errors.dto.ErrorType.SYSTEM;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@RequiredArgsConstructor
public enum ErrorDictionary {

    //system: 02.00.xx
    ERROR_READING_JSON("02.00.01", "Error reading JSON", SYSTEM, INTERNAL_SERVER_ERROR, JsonReadException.class),
    ERROR_READING_FILE("02.00.02", "Error reading JSON", SYSTEM, INTERNAL_SERVER_ERROR, FileReadException.class),

    //no such properties and components: 02.01.xx
    NO_SUCH_REST_CLIENT("02.01.01", "No such rest client", SYSTEM, INTERNAL_SERVER_ERROR, NoSuchRestClientException.class),
    NO_SUCH_REST_CLIENT_ERROR_EXTRACTOR("02.01.02", "No such rest client error extractor", SYSTEM, INTERNAL_SERVER_ERROR, NoSuchRestClientErrorExtractorException.class),
    NO_SUCH_PARAM_MAPPER("02.01.03", "No such param mapper", SYSTEM, BAD_REQUEST, NoSuchParamMapperException.class),

    //business and bad requests: 01.02.xx
    INVALID_FIELD("02.02.01", "Invalid field", BUSINESS, BAD_REQUEST, InvalidFieldException.class),

    //encryption: 01.03.xx
    NO_SUCH_ENCRYPTION_METHOD("02.03.01", "No such encryption method", SYSTEM, INTERNAL_SERVER_ERROR, NoSuchEncryptionMethodException.class),
    AES_NO_SUCH_KEY("02.03.02", "No such AES key", SYSTEM, INTERNAL_SERVER_ERROR, AesNoSuchKeyException.class),
    AES_UNEXPECTED_ENCRYPTION("02.03.03", "Unexpected AES encryption error", SYSTEM, INTERNAL_SERVER_ERROR, AesUnexpectedEncryptionException.class),
    AES_UNEXPECTED_DECRYPTION("02.03.04", "Unexpected AES decryption error", SYSTEM, INTERNAL_SERVER_ERROR, AesUnexpectedDecryptionException.class),
    AES_INVALID_IV_LENGTH("02.03.05", "Invalid IV length", SYSTEM, INTERNAL_SERVER_ERROR, AesInvalidIVLengthException.class),
    RSA_UNEXPECTED_PUBLIC_KEY_READING("02.03.06", "Unexpected RSA public key reading error", SYSTEM, INTERNAL_SERVER_ERROR, RsaUnexpectedPublicKeyReadingException.class),
    RSA_UNEXPECTED_PRIVATE_KEY_READING("02.03.07", "Unexpected RSA private key reading error", SYSTEM, INTERNAL_SERVER_ERROR, RsaUnexpectedPrivateKeyReadingException.class),
    RSA_UNEXPECTED_ENCRYPTION("02.03.08", "Unexpected RSA encryption error", SYSTEM, INTERNAL_SERVER_ERROR, RsaUnexpectedEncryptionException.class),
    RSA_UNEXPECTED_DECRYPTION("02.03.09", "Unexpected RSA decryption error", SYSTEM, INTERNAL_SERVER_ERROR, RsaUnexpectedDecryptionException.class),
    RSA_UNEXPECTED_CIPHER("02.03.10", "Unexpected Cipher creation error", SYSTEM, INTERNAL_SERVER_ERROR, RsaUnexpectedCipherException.class),
    RSA_NO_SUCH_PRIVATE_KEY("02.03.11", "No such RSA private key", SYSTEM, INTERNAL_SERVER_ERROR, RsaNoSuchPrivateKeyException.class),
    RSA_NO_SUCH_PUBLIC_KEY("02.03.12", "No such RSA public key", SYSTEM, INTERNAL_SERVER_ERROR, RsaNoSuchPublicKeyException.class),
    NO_SUCH_ENCRYPTION_STRATEGY("02.03.13", "No such encryption strategy", SYSTEM, INTERNAL_SERVER_ERROR, NoSuchEncryptionStrategyException.class),
    NO_SUCH_CRYPTOGRAPHY_TEMPLATE("02.03.14", "No such cryptography template by feature", SYSTEM, INTERNAL_SERVER_ERROR, NoSuchCryptographyTemplateException.class);

    private final String code;
    private final String message;
    private final ErrorType type;
    private final HttpStatus httpStatus;
    private final Class<? extends GenericException> exceptionClass;

    public static ErrorDictionary parse(Class<? extends GenericException> exceptionClass) {
        return Arrays.stream(ErrorDictionary.values())
            .filter(errorDetail -> errorDetail.getExceptionClass().isAssignableFrom(exceptionClass))
            .findFirst()
            .orElseThrow(() -> new GenericException("No such exception"));
    }
}
