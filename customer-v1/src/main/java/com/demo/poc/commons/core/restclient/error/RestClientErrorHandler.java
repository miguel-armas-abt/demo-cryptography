package com.demo.poc.commons.core.restclient.error;

import com.demo.poc.commons.core.errors.dto.ErrorDto;
import com.demo.poc.commons.core.errors.dto.ErrorOrigin;
import com.demo.poc.commons.core.errors.exceptions.RestClientException;
import com.demo.poc.commons.core.errors.exceptions.NoSuchRestClientErrorExtractorException;
import com.demo.poc.commons.core.errors.selector.RestClientErrorSelector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;
import java.util.Map;

import static com.demo.poc.commons.core.errors.selector.RestClientErrorSelector.selectType;

@Slf4j
@RequiredArgsConstructor
public class RestClientErrorHandler {

  private static final String EMPTY_RESPONSE = "REST Client empty response body";
  private static final String NO_SUCH_ERROR_WRAPPER = "No such error wrapper for REST client error response";

  private final List<RestClientErrorExtractor> restClientErrorExtractors;
  private final RestClientErrorSelector restClientErrorSelector;

  public RestClientException handleError(HttpStatusCodeException httpException,
                                         Class<?> errorWrapperClass,
                                         String serviceName) {

    String jsonBody = httpException.getResponseBodyAsString();

    Map.Entry<String, String> codeAndMessage = getCodeAndMessage(jsonBody, errorWrapperClass);
    String extractedCode = codeAndMessage.getKey();
    String extractedMessage = codeAndMessage.getValue();


    String selectedCode = restClientErrorSelector.selectCode(extractedCode, serviceName);
    String selectedMessage = restClientErrorSelector.selectMessage(selectedCode, extractedMessage, serviceName);
    ErrorOrigin selectedErrorOrigin = selectType(errorWrapperClass);
    HttpStatusCode selectedHttpStatus = HttpStatusCode.valueOf(restClientErrorSelector.selectHttpCode(httpException.getStatusCode().value(), errorWrapperClass, extractedCode, serviceName));

    return new RestClientException(selectedCode, selectedMessage, selectedErrorOrigin, selectedHttpStatus);
  }

  private Map.Entry<String, String> getCodeAndMessage(String jsonBody, Class<?> errorWrapperClass) {
    if (Strings.EMPTY.equals(jsonBody)) {
      return mapUnexpectedResponse(EMPTY_RESPONSE);

    } else {
      return selectExtractor(errorWrapperClass)
          .getCodeAndMessage(jsonBody)
          .orElseGet(() -> {
            log.warn(jsonBody);
            return mapUnexpectedResponse(NO_SUCH_ERROR_WRAPPER);
          });
    }
  }

  private Map.Entry<String, String> mapUnexpectedResponse(String message) {
    return Map.of(ErrorDto.CODE_DEFAULT, message).entrySet().iterator().next();
  }

  private RestClientErrorExtractor selectExtractor(Class<?> errorWrapperClass) {
    return restClientErrorExtractors
        .stream()
        .filter(service -> service.supports(errorWrapperClass))
        .findFirst()
        .orElseThrow(() -> new NoSuchRestClientErrorExtractorException(errorWrapperClass));
  }
}
