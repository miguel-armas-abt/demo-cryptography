package com.demo.poc.encryption.dto.params;

import com.demo.poc.commons.core.validations.headers.DefaultHeaders;
import com.demo.poc.commons.core.validations.utils.ParamName;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class EncryptionParam extends DefaultHeaders implements Serializable {

    @NotEmpty
    @ParamName("encryption-method")
    private String encryptionMethod;

    @NotEmpty
    private String feature;
}
