package com.demo.poc.entrypoint.customer.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDto implements Serializable {

  private Long uniqueCode;

  @Size(min = 2, max = 300, message = "names size must be between 2 and 300")
  @NotNull(message = "names cannot be null")
  private String names;

  @Size(min = 2, max = 300, message = "names size must be between 2 and 300")
  @NotNull(message = "lastNames cannot be null")
  private String lastNames;

  @Pattern(regexp = "^(DNI|CE|PASSPORT)$", message = "documentType must be DNI, CE or PASSPORT")
  @NotNull(message = "documentType cannot be null")
  private String documentType;

  @NotNull(message = "documentNumber cannot be null")
  private Integer documentNumber;

  @NotNull(message = "Flag active cannot be null")
  private Boolean active;

  @NotNull(message = "Password cannot be null")
  private String password;

}
