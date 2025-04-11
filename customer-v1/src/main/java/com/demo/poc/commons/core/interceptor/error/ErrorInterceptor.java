package com.demo.poc.commons.core.interceptor.error;

import static com.demo.poc.commons.custom.exceptions.ErrorDictionary.INVALID_FIELD;
import static com.demo.poc.commons.core.logging.utils.HeaderExtractor.extractTraceHeaders;

import com.demo.poc.commons.core.errors.dto.ErrorDto;
import com.demo.poc.commons.core.errors.exceptions.GenericException;
import com.demo.poc.commons.core.logging.ThreadContextInjector;
import com.demo.poc.commons.custom.properties.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
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
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorInterceptor extends ResponseEntityExceptionHandler {

  private final ApplicationProperties properties;

  @ExceptionHandler({Throwable.class})
  public ResponseEntity<ErrorDto> handleException(Throwable ex, WebRequest request) {
    generateTrace(ex, request);

    ErrorDto error = ErrorDto.getDefaultError(properties);
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    if (ex instanceof ResourceAccessException || ex instanceof ConnectException) {
      httpStatus = HttpStatus.REQUEST_TIMEOUT;
    }

    if(ex instanceof GenericException genericException) {
      error = genericException.getErrorDetail();
      httpStatus = genericException.getHttpStatus();
    }

    return new ResponseEntity<>(error, httpStatus);
  }

  //jakarta validations
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                HttpStatusCode status, WebRequest request) {
    generateTrace(ex, request);

    String errorMessage = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.joining(";"));

    ErrorDto error = ErrorDto.builder()
        .code(INVALID_FIELD.getCode())
        .message(errorMessage)
        .build();
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MissingRequestHeaderException.class)
  public ResponseEntity<ErrorDto> handleMissingRequestHeader(MissingRequestHeaderException exception, WebRequest request) {
    generateTrace(exception, request);

    String message = exception.getBody().getDetail();
    ErrorDto error = ErrorDto.builder()
            .code(INVALID_FIELD.getCode())
            .message(message)
            .build();

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  private static void generateTrace(Throwable exception, WebRequest request) {
    String message = exception.getMessage();
    ThreadContextInjector.populateFromHeaders(extractTraceHeaders(request));

    log.error(message, exception);

    ThreadContext.clearAll();
  }
}
