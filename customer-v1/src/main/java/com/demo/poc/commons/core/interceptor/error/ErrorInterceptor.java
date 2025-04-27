package com.demo.poc.commons.core.interceptor.error;

import com.demo.poc.commons.core.errors.dto.ErrorDto;
import com.demo.poc.commons.core.errors.exceptions.RestClientException;
import com.demo.poc.commons.core.errors.exceptions.GenericException;
import com.demo.poc.commons.core.errors.selector.ResponseErrorSelector;
import com.demo.poc.commons.core.logging.ErrorThreadContextInjector;
import com.demo.poc.commons.core.logging.enums.LoggingType;
import com.demo.poc.commons.custom.properties.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.ConnectException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorInterceptor extends ResponseEntityExceptionHandler {

  private final ErrorThreadContextInjector contextInjector;
  private final ApplicationProperties properties;
  private final ResponseErrorSelector responseErrorSelector;

  @ExceptionHandler({Throwable.class})
  public ResponseEntity<ErrorDto> handleException(Throwable ex, WebRequest request) {
    generateTrace(ex, request);

    ErrorDto error = ErrorDto.getDefaultError(properties);
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    if (ex instanceof ResourceAccessException || ex instanceof ConnectException) {
      httpStatus = HttpStatus.REQUEST_TIMEOUT;
    }

    if (ex instanceof RestClientException restClientException) {
      error = restClientException.getErrorDetail();
      httpStatus = HttpStatus.valueOf(restClientException.getHttpStatusCode().value());
    }

    if (ex instanceof GenericException genericException) {
      error = responseErrorSelector.toErrorDTO(genericException);
      httpStatus = genericException.getHttpStatus();
    }

    return new ResponseEntity<>(error, httpStatus);
  }

  //jakarta validations
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                HttpStatusCode status, WebRequest request) {
    generateTrace(ex, request);
    ErrorDto error = responseErrorSelector.toErrorDTO(ex);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MissingRequestHeaderException.class)
  public ResponseEntity<ErrorDto> handleMissingRequestHeader(MissingRequestHeaderException ex, WebRequest request) {
    generateTrace(ex, request);
    ErrorDto error = responseErrorSelector.toErrorDTO(ex);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  private void generateTrace(Throwable ex, WebRequest request) {
    if (properties.isLoggerPresent(LoggingType.ERROR)) {
      contextInjector.populateFromException(ex, request);
    }
  }
}