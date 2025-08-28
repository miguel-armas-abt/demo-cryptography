package com.demo.service.entrypoint.encryption.service;

import com.demo.service.entrypoint.encryption.dto.response.DecryptionResponseDto;
import com.demo.service.entrypoint.encryption.dto.response.EncryptionResponseDto;
import com.demo.service.entrypoint.encryption.strategies.EncryptionMethod;

public interface EncryptionService {

  EncryptionResponseDto encrypt(EncryptionMethod encryptionMethod,
                                String feature,
                                String valueToEncrypt);

  DecryptionResponseDto decrypt(EncryptionMethod encryptionMethod,
                                String feature,
                                String cipherMessage);
}
