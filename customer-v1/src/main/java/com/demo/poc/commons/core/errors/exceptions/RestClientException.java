package com.demo.poc.commons.core.errors.exceptions;

import java.io.Serial;

import com.demo.poc.commons.core.errors.dto.ErrorDto;
import lombok.Getter;

import org.springframework.http.HttpStatusCode;

@Getter
public class RestClientException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 480700693894159856L;

  private final ErrorDto errorDetail;
  private final HttpStatusCode httpStatusCode;

  public RestClientException(String code, String message, HttpStatusCode httpStatusCode) {
    super(message);
    this.errorDetail = ErrorDto.builder()
        .code(code)
        .message(message)
        .build();
    this.httpStatusCode = httpStatusCode;
  }
}
