package com.demo.poc.commons.core.errors.selector;

import com.demo.poc.commons.core.errors.dto.ErrorDto;
import com.demo.poc.commons.core.errors.dto.ErrorOrigin;
import com.demo.poc.commons.core.properties.ConfigurationBaseProperties;
import com.demo.poc.commons.core.properties.restclient.RestClient;
import com.demo.poc.commons.core.properties.restclient.RestClientError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class RestClientErrorSelector {

  private static final String[] HTTP_ALLOWED_CODES = {"400", "401", "403", "404"};
  private static final int HTTP_CONFLICT_CODE = 409;

  private final ConfigurationBaseProperties properties;

  public String selectCode(String errorCode, String serviceName) {

    String code = Optional.ofNullable(errorCode).orElseGet(() -> ErrorDto.CODE_DEFAULT);
    RestClientError defaultError = RestClientError.builder().customCode(code).build();

    return findErrors(serviceName)
        .map(errors -> Optional.of(errors)
            .map(error -> error.getOrDefault(code, defaultError).getCustomCode())
            .orElseGet(() -> code))
        .orElseGet(() -> code);
  }

  public String selectMessage(String errorCode, String errorMessage, String serviceName) {

    String defaultMessage = ErrorDto.getDefaultError(properties).getMessage();
    RestClientError defaultError = RestClientError.builder().message(defaultMessage).build();
    String message = Optional.ofNullable(errorMessage).orElseGet(() -> defaultMessage);

    return findErrors(serviceName)
        .map(errors -> Optional.of(errors)
            .map(error -> error.getOrDefault(errorCode, defaultError))
            .map(RestClientError::getMessage)
            .orElseGet(() -> message))
        .orElseGet(() -> message);
  }

  public static ErrorOrigin selectType(Class<?> errorWrapperClass) {
    return (errorWrapperClass.isAssignableFrom(ErrorDto.class))
        ? ErrorOrigin.PARTNER
        : ErrorOrigin.THIRD_PARTY;
  }

  public int selectHttpCode(int httpCode,
                            Class<?> errorWrapperClass,
                            String errorCode,
                            String serviceName) {

    if (errorWrapperClass.isAssignableFrom(ErrorDto.class))
      return httpCode;

    if (Arrays.asList(HTTP_ALLOWED_CODES).contains(String.valueOf(httpCode)))
      return httpCode;

    return findErrors(serviceName)
        .map(errors -> errors.getOrDefault(errorCode, RestClientError.builder().httpCode(httpCode).build()))
        .map(RestClientError::getHttpCode)
        .orElse(HTTP_CONFLICT_CODE);
  }

  public Optional<Map<String, RestClientError>> findErrors(String serviceName) {
    return Optional.ofNullable(properties.getRestClients().get(serviceName))
        .map(RestClient::getErrors);
  }

  public Pair<String, String> getDefaultResponse(ErrorDto defaultError, String logMessage) {
    log.warn(logMessage);
    return Pair.of(ErrorDto.CODE_DEFAULT, defaultError.getMessage());
  }
}