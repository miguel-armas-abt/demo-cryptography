package com.demo.service.entrypoint.encryption.strategies.rsa;

import com.demo.service.entrypoint.encryption.exceptions.RsaUnexpectedCipherException;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.MGF1ParameterSpec;

@Component
@RequiredArgsConstructor
public class CipherGenerator {

  public Cipher getCipher(final int mode, final Key key) {
    Cipher cipher;
    try {
      cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING", new BouncyCastleProvider());
      OAEPParameterSpec oaePParameterSpec = new OAEPParameterSpec("SHA-256", "MGF1",
          MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
      cipher.init(mode, key, oaePParameterSpec);
      return cipher;
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException ex) {
      throw new RsaUnexpectedCipherException(ex);
    }
  }
}
