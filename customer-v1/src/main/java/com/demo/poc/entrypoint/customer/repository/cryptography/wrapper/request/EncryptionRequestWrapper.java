package com.demo.poc.entrypoint.customer.repository.cryptography.wrapper.request;

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
public class EncryptionRequestWrapper implements Serializable {

    private String value;
}
