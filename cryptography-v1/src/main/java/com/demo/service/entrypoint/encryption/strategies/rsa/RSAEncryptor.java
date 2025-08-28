package com.demo.service.entrypoint.encryption.strategies.rsa;

import com.demo.service.entrypoint.encryption.exceptions.RsaUnexpectedDecryptionException;
import com.demo.service.entrypoint.encryption.exceptions.RsaUnexpectedEncryptionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;

@Slf4j
@RequiredArgsConstructor
@Component
public class RSAEncryptor {

    private final CipherGenerator cipherGenerator;

    public String encrypt(final PublicKey publicKey, final String value) {

        String encryptedValue;
        try {
            Cipher cipher = cipherGenerator.getCipher(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedValueBytes = cipher.doFinal(value.getBytes());
            encryptedValue = new String(Base64.encode(encryptedValueBytes));
        } catch (Exception ex) {
            throw new RsaUnexpectedEncryptionException(ex);
        }
        return encryptedValue;
    }

    public String decrypt(final PrivateKey privateKey, final String cipherMessage) {
        String decryptedValue;
        try {
            Cipher cipher = cipherGenerator.getCipher(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedValueBytes = cipher.doFinal(Base64.decode(cipherMessage));
            decryptedValue = new String(decryptedValueBytes);
        } catch (Exception ex) {
            throw new RsaUnexpectedDecryptionException(ex);
        }
        return decryptedValue;
    }

}
