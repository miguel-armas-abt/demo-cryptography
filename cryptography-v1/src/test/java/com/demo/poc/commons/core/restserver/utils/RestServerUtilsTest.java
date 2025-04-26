package com.demo.poc.commons.core.restserver.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RestServerUtilsTest {

  @ParameterizedTest(name = "#{index} – Header [{0} = {1}]")
  @CsvSource({
      "h1,v1",
      "h2,v2"
  })
  @DisplayName("Given HttpServletRequest with headers, when extractHeadersAsMap, then returns the correct Map")
  void givenHttpServletRequestWithHeaders_whenExtractHeadersAsMap_thenReturnsCorrectMap(String headerName,
                                                                                        String headerValue) {
    // Arrange
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader(headerName, headerValue);

    // Act
    Map<String, String> result = RestServerUtils.extractHeadersAsMap(request);

    // Assert
    assertEquals(1, result.size());
    assertEquals(headerValue, result.get(headerName));
  }

  @ParameterizedTest(name = "#{index} – QueryParam [{0} = {1}]")
  @CsvSource({
      "p1,value1",
      "p2,value2"
  })
  @DisplayName("Given HttpServletRequest with query params, when extractQueryParamsAsMap, then returns correct Map")
  void givenHttpServletRequestWithQueryParams_whenExtractQueryParamsAsMap_thenReturnsCorrectMap(String paramName,
                                                                                                String paramValue) {
    // Arrange
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addParameter(paramName, paramValue);

    // Act
    Map<String, String> result = RestServerUtils.extractQueryParamsAsMap(request);

    // Assert
    assertEquals(1, result.size());
    assertEquals(paramValue, result.get(paramName));
  }

  @Test
  @DisplayName("Given HttpServletRequest without headers or params, when extract methods, then return empty maps")
  void givenEmptyHttpServletRequest_whenExtract_thenReturnEmptyMaps() {
    // Arrange
    MockHttpServletRequest request = new MockHttpServletRequest();

    // Act
    Map<String, String> headers = RestServerUtils.extractHeadersAsMap(request);
    Map<String, String> params = RestServerUtils.extractQueryParamsAsMap(request);

    // Assert
    assertTrue(headers.isEmpty());
    assertTrue(params.isEmpty());
  }
}
