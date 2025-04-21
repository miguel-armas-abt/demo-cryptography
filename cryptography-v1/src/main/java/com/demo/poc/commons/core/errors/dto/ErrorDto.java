package com.demo.poc.commons.core.errors.dto;

import com.demo.poc.commons.custom.properties.ApplicationProperties;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto implements Serializable {

    public static final String CODE_DEFAULT = "Default";

    private String code;

    private String message;

    public static ErrorDto getDefaultError(ApplicationProperties properties) {
        return ErrorDto
          .builder()
          .code(CODE_DEFAULT)
          .message(getMatchMessage(properties, CODE_DEFAULT))
          .build();
    }

    public static String getMatchMessage(ApplicationProperties properties, String errorCode) {
        return properties
          .getErrorMessages()
          .get(errorCode);
    }
}
