package com.demo.poc.entrypoint.customer.params;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
public class CustomerParam implements Serializable {

    @Pattern(regexp = "^(DNI|CE|PASSPORT)$")
    private String documentType;
}
