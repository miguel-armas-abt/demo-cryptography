package com.demo.service.entrypoint.customer.repository.cryptography.wrapper.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecryptionResponseWrapper implements Serializable {

    private String value;
}
