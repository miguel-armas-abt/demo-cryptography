package com.demo.poc.commons.core.restclient.utils;

import com.demo.poc.commons.core.properties.restclient.HeaderTemplate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HeadersFillerTest {

  @ParameterizedTest(name = "#{index} – [{0} = {1}]")
  @CsvSource({
      "Subscription-Key, fake-key",
      "Subscription-Secret, fake-secret"
  })
  @DisplayName("Given only provided params, when fillHeaders, then returns only those provided")
  void givenOnlyProvidedParams_whenFillHeaders_thenReturnsOnlyProvided(String headerName, String headerValue) {
    // Arrange
    HeaderTemplate template = new HeaderTemplate();
    template.setProvided(Map.of(headerName, headerValue));

    Map<String, String> current = new HashMap<>();

    // Act
    Map<String, String> result = HeadersFiller.fillHeaders(template, current);

    // Assert
    assertEquals(1, result.size());
    assertEquals(headerValue, result.get(headerName));
  }

  @ParameterizedTest(name = "#{index} – [{1} → {0}] = [{2}]")
  @CsvSource({
      "channelId, channel-id, APP",
      "deviceId, deviceId, 127.0.0.1"
  })
  @DisplayName("Given only forwarded params, when fillHeaders, then returns only those forwarded if present")
  void givenOnlyForwardedParams_whenFillHeaders_thenReturnsOnlyForwarded(String forwardedKey,
                                                                         String headerName,
                                                                         String headerValue) {
    // Arrange
    HeaderTemplate template = new HeaderTemplate();
    template.setForwarded(Map.of(forwardedKey, headerName));

    Map<String, String> current = new HashMap<>();
    current.put(headerName, headerValue);

    // Act
    Map<String, String> result = HeadersFiller.fillHeaders(template, current);

    // Assert
    assertEquals(1, result.size());
    assertEquals(headerValue, result.get(forwardedKey));
  }

  @ParameterizedTest(name = "#{index} – provided=[{0} = {1}], forwarded=[{3} → {2}] = [{4}]")
  @CsvSource({
      "p1, v1, f1, h1, hv1",
      "p2, v2, f2, h2, hv2"
  })
  @DisplayName("Given provided and forwarded params, when fillHeaders, then returns both sets correctly")
  void given_providedAndForwardedParams_when_fillHeaders_then_returnsBoth(String providedKey,
                                                                          String providedValue,
                                                                          String forwardedKey,
                                                                          String headerName,
                                                                          String headerValue) {
    // Arrange
    HeaderTemplate template = new HeaderTemplate();
    template.setProvided(Map.of(providedKey, providedValue));
    template.setForwarded(Map.of(forwardedKey, headerName));

    Map<String, String> current = new HashMap<>();
    current.put(headerName, headerValue);

    // Act
    Map<String, String> result = HeadersFiller.fillHeaders(template, current);

    // Assert
    assertEquals(2, result.size());
    assertEquals(providedValue, result.get(providedKey));
    assertEquals(headerValue, result.get(forwardedKey));
  }
}