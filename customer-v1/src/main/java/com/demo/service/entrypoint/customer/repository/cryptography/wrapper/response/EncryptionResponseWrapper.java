package com.demo.service.entrypoint.customer.repository.cryptography.wrapper.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EncryptionResponseWrapper implements Serializable {

  private String cipherMessage;
}
