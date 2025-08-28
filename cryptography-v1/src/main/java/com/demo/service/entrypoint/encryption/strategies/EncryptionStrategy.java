package com.demo.service.entrypoint.encryption.strategies;

import com.demo.service.commons.properties.crypto.CryptographyTemplate;

public interface EncryptionStrategy {

    String encrypt(String value, CryptographyTemplate feature);
    String decrypt(String cipherMessage, CryptographyTemplate feature);

    boolean supports(EncryptionMethod encryptionMethod);
}
