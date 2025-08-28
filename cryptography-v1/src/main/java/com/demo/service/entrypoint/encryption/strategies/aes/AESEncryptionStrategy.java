package com.demo.service.entrypoint.encryption.strategies.aes;

import com.demo.service.entrypoint.encryption.exceptions.AesNoSuchKeyException;
import com.demo.service.commons.properties.crypto.CryptographyTemplate;
import com.demo.service.entrypoint.encryption.strategies.EncryptionMethod;
import com.demo.service.entrypoint.encryption.strategies.EncryptionStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.demo.service.entrypoint.encryption.strategies.EncryptionMethod.AES;

@Component
@RequiredArgsConstructor
public class AESEncryptionStrategy implements EncryptionStrategy {

    @Override
    public String encrypt(String value, CryptographyTemplate feature) {
        return AESEncryptor.encrypt(searchKey(feature), value);
    }

    @Override
    public String decrypt(String cipherMessage, CryptographyTemplate feature) {
        return AESEncryptor.decrypt(searchKey(feature), cipherMessage);
    }

    @Override
    public boolean supports(EncryptionMethod encryptionMethod) {
        return AES.equals(encryptionMethod);
    }

    private String searchKey(CryptographyTemplate feature) {
        return Optional.ofNullable(feature.getAes())
            .orElseThrow(AesNoSuchKeyException::new);
    }
}
