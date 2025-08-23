package com.demo.poc.entrypoint.encryption.strategies;

import com.demo.poc.entrypoint.encryption.exceptions.NoSuchEncryptionMethodException;
import java.util.Arrays;
import java.util.Locale;

public enum EncryptionMethod {
    RSA, AES;

    public static EncryptionMethod parse(String encryptionMethod) {
        return Arrays.stream(values())
            .filter(method -> method.name().equals(encryptionMethod.toUpperCase(Locale.ROOT)))
            .findFirst()
            .orElseThrow(NoSuchEncryptionMethodException::new);
    }
}
