package com.demo.poc.commons.core.errors.dto;

import com.demo.poc.commons.core.properties.ConfigurationBaseProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto implements Serializable {

  @Serial
  private static final long serialVersionUID = 281390055501829628L;

  public static final String CODE_DEFAULT = "Default";

  @JsonProperty("type")
  private ErrorType type;

  private String code;

  private String message;

  public static ErrorDto getDefaultError(ConfigurationBaseProperties properties) {
    return ErrorDto
        .builder()
        .code(CODE_DEFAULT)
        .message(getMatchMessage(properties, CODE_DEFAULT))
        .type(ErrorType.SYSTEM)
        .build();
  }

  public static String getMatchMessage(ConfigurationBaseProperties properties, String errorCode) {
    return properties
        .getErrorMessages()
        .get(errorCode);
  }
}