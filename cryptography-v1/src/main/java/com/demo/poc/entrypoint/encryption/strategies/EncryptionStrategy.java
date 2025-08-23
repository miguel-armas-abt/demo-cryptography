package com.demo.poc.entrypoint.encryption.strategies;

import com.demo.poc.commons.properties.crypto.CryptographyTemplate;

public interface EncryptionStrategy {

    String encrypt(String value, CryptographyTemplate feature);
    String decrypt(String cipherMessage, CryptographyTemplate feature);

    boolean supports(EncryptionMethod encryptionMethod);
}
