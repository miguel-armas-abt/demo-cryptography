package com.demo.poc.entrypoint.encryption.strategies.rsa.keys;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface KeyFileReader {

    PublicKey getPublicKey(final String resource);
    PrivateKey getPrivateKey(final String resource);

}
