package com.demo.poc.commons.core.logging.obfuscation.header;

import com.demo.poc.commons.core.constants.Symbol;
import com.demo.poc.commons.core.logging.obfuscation.constants.ObfuscationConstant;
import com.demo.poc.commons.core.properties.logging.ObfuscationTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HeaderObfuscatorTest {

  @Test
  @DisplayName("Given null or empty template headers, when process, then returns all headers unmodified")
  void givenNoSensitiveHeaders_whenProcess_thenReturnsOriginal() {
    // Arrange
    ObfuscationTemplate template = new ObfuscationTemplate();
    Map<String, String> headers = new LinkedHashMap<>();
    headers.put("A", "1");
    headers.put("B", "2");

    // Act
    String result = HeaderObfuscator.process(template, headers);

    // Assert
    assertEquals("A=1, B=2", result);
  }

  @ParameterizedTest(name = "# {index} – header={0}, value={1}, expected={2}")
  @CsvSource({
      "Authorization,Bearer abcd123,Bea*****123",
      "Password,abc123,*****"
  })
  @DisplayName("Given a sensitive header, when value longer or shorter than 6, then masks correctly")
  void givenSensitiveHeader_whenProcess_thenMasksCorrectly(String key,
                                                           String value,
                                                           String expectedMasked) {
    // Arrange
    ObfuscationTemplate template = new ObfuscationTemplate();
    template.setHeaders(Set.of(key));
    Map<String, String> headers = Map.of(key, value);

    // Act
    String result = HeaderObfuscator.process(template, headers);

    // Assert
    assertEquals(key + Symbol.EQUAL + expectedMasked, result);
  }

  @CsvSource({
      "X-Null"
  })
  @ParameterizedTest
  @DisplayName("Given sensitive header with null value, when process, then uses warning text")
  void givenSensitiveHeaderWithNullValue_whenProcess_thenUsesWarning(String key) {
    // Arrange
    ObfuscationTemplate template = new ObfuscationTemplate();
    template.setHeaders(Set.of(key));
    Map<String, String> headers = new HashMap<>();
    headers.put(key, null);

    // Act
    String result = HeaderObfuscator.process(template, headers);

    // Assert
    assertEquals("X-Null=" + ObfuscationConstant.NULL_WARNING, result);
  }

  @CsvSource({
      "S"
  })
  @ParameterizedTest
  @DisplayName("Given mix of sensitive and non-sensitive headers, when process, then masks only sensitive ones")
  void givenMixedHeaders_whenProcess_thenMaskSelective(String key) {
    // Arrange
    ObfuscationTemplate template = new ObfuscationTemplate();
    template.setHeaders(Set.of(key));
    Map<String, String> headers = new LinkedHashMap<>();
    headers.put(key, "secretpass");
    headers.put("N", "normal");

    // Act
    String result = HeaderObfuscator.process(template, headers);

    // Assert
    String masked = "sec*****ass";
    assertEquals("S=" + masked + ", N=normal", result);
  }
}
