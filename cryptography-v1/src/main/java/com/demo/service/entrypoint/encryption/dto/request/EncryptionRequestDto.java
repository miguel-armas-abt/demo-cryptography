package com.demo.service.entrypoint.encryption.dto.request;

import jakarta.validation.constraints.NotEmpty;
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
public class EncryptionRequestDto implements Serializable {

  @NotEmpty(message = "Value must not be empty")
  private String value;
}
