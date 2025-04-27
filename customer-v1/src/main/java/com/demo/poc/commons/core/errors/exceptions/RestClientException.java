package com.demo.poc.commons.core.errors.exceptions;

import com.demo.poc.commons.core.errors.dto.ErrorDto;
import com.demo.poc.commons.core.errors.dto.ErrorType;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.io.Serial;

@Getter
public class RestClientException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 480700693894159856L;

  private final ErrorDto errorDetail;
  private final HttpStatusCode httpStatusCode;

  public RestClientException(String code, String message, ErrorType errorType, HttpStatusCode httpStatusCode) {
    super(message);
    this.errorDetail = ErrorDto.builder()
        .type(errorType)
        .code(code)
        .message(message)
        .build();
    this.httpStatusCode = httpStatusCode;
  }
}
