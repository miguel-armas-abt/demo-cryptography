package com.demo.poc.entrypoint.encryption.params;

import com.demo.poc.commons.core.validations.headers.DefaultHeaders;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class EncryptionHeader extends DefaultHeaders implements Serializable {

    @NotEmpty
    private String encryptionMethod;

    @NotEmpty
    private String feature;
}
