package com.demo.service.entrypoint.encryption.strategies.rsa.keys;

import com.demo.service.entrypoint.encryption.exceptions.RsaUnexpectedPrivateKeyReadingException;
import com.demo.service.entrypoint.encryption.exceptions.RsaUnexpectedPublicKeyReadingException;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@Slf4j
@Component
public class Base64KeyReader implements KeyFileReader {

    private final JcaPEMKeyConverter keyConverter;

    public Base64KeyReader() {
        this.keyConverter = new JcaPEMKeyConverter();
    }

    @Override
    public PublicKey getPublicKey(String base64PublicKey) {
        byte[] decodedKey = Base64.getDecoder().decode(base64PublicKey);
        return readPublicKey(decodedKey);
    }

    @Override
    public PrivateKey getPrivateKey(String base64PrivateKey) {
        byte[] decodedKey = Base64.getDecoder().decode(base64PrivateKey);
        return readPrivateKey(decodedKey);
    }

    private PublicKey readPublicKey(final byte[] keyBytes) {
        PublicKey publicKey;
        try (PEMParser pemParser = new PEMParser(new StringReader(new String(keyBytes, StandardCharsets.UTF_8)))) {
            SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(pemParser.readObject());
            publicKey = this.keyConverter.getPublicKey(publicKeyInfo);
        } catch (IOException ex) {
            throw new RsaUnexpectedPublicKeyReadingException(ex);
        }
        return publicKey;
    }

    private PrivateKey readPrivateKey(final byte[] keyBytes) {
        PrivateKey privateKey = null;
        try (PEMParser pemParser = new PEMParser(new StringReader(new String(keyBytes, StandardCharsets.UTF_8)))) {
            Object object = pemParser.readObject();

            if (object instanceof PEMKeyPair) {
                PEMKeyPair pemKeyPair = (PEMKeyPair) object;
                privateKey = this.keyConverter.getKeyPair(pemKeyPair).getPrivate();
            }

            if (object instanceof PrivateKeyInfo) {
                PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) object;
                privateKey = this.keyConverter.getPrivateKey(privateKeyInfo);
            }

        } catch (IOException ex) {
            throw new RsaUnexpectedPrivateKeyReadingException(ex);
        }
        return privateKey;
    }
}
