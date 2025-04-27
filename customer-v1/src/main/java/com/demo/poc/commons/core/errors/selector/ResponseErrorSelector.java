package com.demo.poc.commons.core.errors.selector;

import com.demo.poc.commons.core.errors.dto.ErrorDto;
import com.demo.poc.commons.core.errors.dto.ErrorType;
import com.demo.poc.commons.custom.exceptions.ErrorDictionary;
import com.demo.poc.commons.core.errors.exceptions.GenericException;
import com.demo.poc.commons.core.properties.ConfigurationBaseProperties;
import com.demo.poc.commons.core.properties.ProjectType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.demo.poc.commons.core.errors.dto.ErrorDto.CODE_DEFAULT;

@RequiredArgsConstructor
public class ResponseErrorSelector {

  private final ConfigurationBaseProperties properties;

  public <T extends Throwable> ErrorDto toErrorDTO(T exception) {
    ErrorDto error = extractError(exception);
    String selectedCode = selectCustomCode(error);
    error.setCode(selectedCode);

    String selectedMessage = selectMessage(error);
    error.setMessage(selectedMessage);

    return error;
  }

  private String selectCustomCode(ErrorDto error) {
    ProjectType projectType = selectProjectType();

    return ProjectType.BFF.equals(projectType)
        ? CODE_DEFAULT
        : Optional.ofNullable(error)
        .map(ErrorDto::getCode)
        .orElseGet(() -> CODE_DEFAULT);
  }

  private String selectMessage(ErrorDto error) {
    String defaultMessage = ErrorDto.getDefaultError(properties).getMessage();
    ProjectType projectType = selectProjectType();

    if (ProjectType.BFF.equals(projectType))
      return defaultMessage;

    return Optional.ofNullable(ErrorDto.getMatchMessage(properties, error.getCode()))
        .orElseGet(() -> Optional.ofNullable(error.getMessage()).orElse(defaultMessage));
  }

  private ProjectType selectProjectType() {
    return Optional.ofNullable(this.properties.getProjectType()).orElseGet(() -> ProjectType.MS);
  }

  private static <T extends Throwable> ErrorDto extractError(T exception) {
    ErrorDto error = null;

    if (exception instanceof GenericException genericException)
      error = genericException.getErrorDetail();

    if (exception instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
      error = extractError(methodArgumentNotValidException);
    }

    if (exception instanceof MissingRequestHeaderException missingRequestHeaderException) {
      error = extractError(missingRequestHeaderException);
    }

    return error;
  }

  private static ErrorDto extractError(MethodArgumentNotValidException exception) {
    String message = exception.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
        .collect(Collectors.joining(";"));

    return ErrorDto.builder()
        .type(ErrorType.BUSINESS)
        .code(ErrorDictionary.INVALID_FIELD.getCode())
        .message(message)
        .build();
  }

  private static ErrorDto extractError(MissingRequestHeaderException exception) {
    return ErrorDto.builder()
            .type(ErrorType.BUSINESS)
            .code(ErrorDictionary.INVALID_FIELD.getCode())
            .message(exception.getBody().getDetail())
            .build();
  }
}
