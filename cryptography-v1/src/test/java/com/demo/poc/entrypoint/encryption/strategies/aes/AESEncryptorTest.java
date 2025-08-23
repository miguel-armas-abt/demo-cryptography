package com.demo.poc.encryption.strategies.aes;

import com.demo.poc.entrypoint.encryption.strategies.aes.AESEncryptor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class AESEncryptorTest {

    public static final String AES_ENCRYPTION_KEY = "VcadipXDNrD67PMRcl4YvjPFTfYKLPBd";

    @ParameterizedTest
    @CsvSource(value = {
        "dummyMessage",
    })
    @DisplayName("Given message, when encrypt, then return cipher message")
    void givenMessage_WhenEncrypt_ThenReturnCipherMessage(String message) {
        //Act
        byte[] key = {47,91,36,95,61,49,59,63,105,108,112,45,37,58,64,42};

        // Convertir byte[] a String usando Base64
        String encodedKey = Base64.getEncoder().encodeToString(key);

        String cipherMessage = AESEncryptor.encrypt(encodedKey, message);
        log.info("cipher message: {}", cipherMessage);

        String actualMessage = AESEncryptor.decrypt(encodedKey, cipherMessage);

        //Assert
        assertEquals(message, actualMessage);
    }
}
