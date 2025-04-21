package com.demo.poc.encryption.rest;

import com.demo.poc.commons.core.restserver.utils.ServerHeaderExtractor;
import com.demo.poc.commons.core.validations.headers.HeaderValidator;
import com.demo.poc.encryption.dto.params.EncryptionParam;
import com.demo.poc.encryption.dto.request.DecryptionRequestDto;
import com.demo.poc.encryption.dto.request.EncryptionRequestDto;
import com.demo.poc.encryption.dto.response.DecryptionResponseDto;
import com.demo.poc.encryption.dto.response.EncryptionResponseDto;
import com.demo.poc.encryption.service.EncryptionService;
import com.demo.poc.encryption.strategies.EncryptionMethod;
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

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/poc/cryptography/v1/encryption", produces = MediaType.APPLICATION_JSON_VALUE)
public class EncryptionRestService {

  private final EncryptionService service;
  private final HeaderValidator headerValidator;

  @PostMapping("encrypt")
  public ResponseEntity<EncryptionResponseDto> encrypt(HttpServletRequest servletRequest,
                                                       @Valid @RequestBody EncryptionRequestDto encryptionRequest) {
    Map<String, String> headers = ServerHeaderExtractor.extractHeadersAsMap(servletRequest);
    EncryptionParam params = headerValidator.validateAndRetrieve(headers, EncryptionParam.class);

    EncryptionMethod method = EncryptionMethod.parse(params.getEncryptionMethod());
    return ResponseEntity.ok(service.encrypt(method, params.getFeature(), encryptionRequest.getValue()));
  }

  @PostMapping("decrypt")
  public ResponseEntity<DecryptionResponseDto> decrypt(HttpServletRequest servletRequest,
                                                       @Valid @RequestBody DecryptionRequestDto decryptionRequest) {
    Map<String, String> headers = ServerHeaderExtractor.extractHeadersAsMap(servletRequest);
    EncryptionParam params = headerValidator.validateAndRetrieve(headers, EncryptionParam.class);

    EncryptionMethod method = EncryptionMethod.parse(params.getEncryptionMethod());
    return ResponseEntity.ok(service.decrypt(method, params.getFeature(), decryptionRequest.getCipherMessage()));
  }

}
