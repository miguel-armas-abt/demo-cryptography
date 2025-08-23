package com.demo.poc.entrypoint.customer.dto.response;

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
public class CustomerResponseDto implements Serializable {

  private Long uniqueCode;

  private String names;

  private String lastNames;

  private String documentType;

  private Integer documentNumber;

}
