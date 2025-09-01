package com.demo.service.entrypoint.encryption.rest;

import com.demo.commons.validations.ParamValidator;
import com.demo.service.entrypoint.encryption.params.EncryptionHeader;
import com.demo.service.entrypoint.encryption.dto.request.DecryptionRequestDto;
import com.demo.service.entrypoint.encryption.dto.request.EncryptionRequestDto;
import com.demo.service.entrypoint.encryption.dto.response.DecryptionResponseDto;
import com.demo.service.entrypoint.encryption.dto.response.EncryptionResponseDto;
import com.demo.service.entrypoint.encryption.service.EncryptionService;
import com.demo.service.entrypoint.encryption.strategies.EncryptionMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/poc/cryptography/v1/encryption", produces = MediaType.APPLICATION_JSON_VALUE)
public class EncryptionRestService {

  private final EncryptionService service;
  private final ParamValidator paramValidator;

  @PostMapping("encrypt")
  public ResponseEntity<EncryptionResponseDto> encrypt(HttpServletRequest servletRequest,
                                                       @Valid @RequestBody EncryptionRequestDto encryptionRequest) {
    EncryptionHeader headers = paramValidator.validateHeadersAndGet(servletRequest, EncryptionHeader.class).getKey();
    EncryptionMethod method = EncryptionMethod.parse(headers.getEncryptionMethod());
    return ResponseEntity.ok(service.encrypt(method, headers.getFeature(), encryptionRequest.getValue()));
  }

  @PostMapping("decrypt")
  public ResponseEntity<DecryptionResponseDto> decrypt(HttpServletRequest servletRequest,
                                                       @Valid @RequestBody DecryptionRequestDto decryptionRequest) {
    EncryptionHeader headers = paramValidator.validateHeadersAndGet(servletRequest, EncryptionHeader.class).getKey();
    EncryptionMethod method = EncryptionMethod.parse(headers.getEncryptionMethod());
    return ResponseEntity.ok(service.decrypt(method, headers.getFeature(), decryptionRequest.getCipherMessage()));
  }

}
