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
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@RequiredArgsConstructor
public enum ErrorDictionary {

    //system=00
    INVALID_FIELD("02.00.01", "Invalid field", BAD_REQUEST, InvalidFieldException.class),
    NO_SUCH_REST_CLIENT("01.00.02", "No such rest client", INTERNAL_SERVER_ERROR, NoSuchRestClientException.class),
    NO_SUCH_CACHE_CONFIG("01.00.03", "No such cache config", INTERNAL_SERVER_ERROR, NoSuchCacheConfigException.class),
    ERROR_MAPPING_REFLECTIVE_PARAMS("01.00.04", "Error mapping reflective params", INTERNAL_SERVER_ERROR, ReflectiveParamMappingException.class),
    ERROR_ASSIGN_REFLECTIVE_PARAMS("01.00.05", "Error assign reflective params", INTERNAL_SERVER_ERROR, ReflectiveParamAssignmentException.class),

    //custom=01
    NO_SUCH_ENCRYPTION_METHOD("02.01.01", "No such encryption method", INTERNAL_SERVER_ERROR, NoSuchEncryptionMethodException.class),
    AES_NO_SUCH_KEY("02.01.02", "No such AES key", INTERNAL_SERVER_ERROR, AesNoSuchKeyException.class),
    AES_UNEXPECTED_ENCRYPTION("02.01.03", "Unexpected AES encryption error", INTERNAL_SERVER_ERROR, AesUnexpectedEncryptionException.class),
    AES_UNEXPECTED_DECRYPTION("02.01.04", "Unexpected AES decryption error", INTERNAL_SERVER_ERROR, AesUnexpectedDecryptionException.class),
    AES_INVALID_IV_LENGTH("02.01.05", "Invalid IV length", INTERNAL_SERVER_ERROR, AesInvalidIVLengthException.class),
    RSA_UNEXPECTED_PUBLIC_KEY_READING("02.01.06", "Unexpected RSA public key reading error", INTERNAL_SERVER_ERROR, RsaUnexpectedPublicKeyReadingException.class),
    RSA_UNEXPECTED_PRIVATE_KEY_READING("02.01.07", "Unexpected RSA private key reading error", INTERNAL_SERVER_ERROR, RsaUnexpectedPrivateKeyReadingException.class),
    RSA_UNEXPECTED_ENCRYPTION("02.01.08", "Unexpected RSA encryption error", INTERNAL_SERVER_ERROR, RsaUnexpectedEncryptionException.class),
    RSA_UNEXPECTED_DECRYPTION("02.01.09", "Unexpected RSA decryption error", INTERNAL_SERVER_ERROR, RsaUnexpectedDecryptionException.class),
    RSA_UNEXPECTED_CIPHER("02.01.10", "Unexpected Cipher creation error", INTERNAL_SERVER_ERROR, RsaUnexpectedCipherException.class),
    RSA_NO_SUCH_PRIVATE_KEY("02.01.11", "No such RSA private key", INTERNAL_SERVER_ERROR, RsaNoSuchPrivateKeyException.class),
    RSA_NO_SUCH_PUBLIC_KEY("02.01.12", "No such RSA public key", INTERNAL_SERVER_ERROR, RsaNoSuchPublicKeyException.class),
    NO_SUCH_ENCRYPTION_STRATEGY("02.01.13", "No such encryption strategy", INTERNAL_SERVER_ERROR, NoSuchEncryptionStrategyException.class),
    NO_SUCH_CRYPTOGRAPHY_TEMPLATE("02.01.14", "No such cryptography template by feature", INTERNAL_SERVER_ERROR, NoSuchCryptographyTemplateException.class);

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
