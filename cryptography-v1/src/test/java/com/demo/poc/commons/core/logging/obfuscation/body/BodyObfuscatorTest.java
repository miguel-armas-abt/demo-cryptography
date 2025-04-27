package com.demo.poc.commons.core.logging.obfuscation.body;

import com.demo.poc.commons.core.properties.logging.ObfuscationTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;

import static com.demo.poc.commons.core.logging.obfuscation.constants.ObfuscationConstant.JSON_SEGMENT_SPLITTER_REGEX;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class BodyObfuscatorTest {

  private static final String JSON_SHORT_VALUES = "{\"field\":\"value\"}";

  @Test
  @DisplayName("Given no bodyFields set, when process, then returns original JSON")
  void givenNoBodyFields_whenProcess_thenReturnsOriginal() {
    // Arrange
    ObfuscationTemplate template = new ObfuscationTemplate();
    String json = JSON_SHORT_VALUES;

    // Act
    String result = BodyObfuscator.process(template, json);

    // Assert
    assertEquals(json, result);
  }

  @CsvSource({
      "field"
  })
  @ParameterizedTest
  @DisplayName("Given empty JSON body, when process, then returns empty string")
  void givenEmptyJsonBody_whenProcess_thenReturnsEmpty(String path) {
    // Arrange
    ObfuscationTemplate template = new ObfuscationTemplate();
    template.setBodyFields(Set.of(path));
    String json = StringUtils.EMPTY;

    // Act
    String result = BodyObfuscator.process(template, json);

    // Assert
    assertEquals(json, result);
  }

  @CsvSource({
      "any.field"
  })
  @ParameterizedTest
  @DisplayName("Given invalid JSON body, when process, then returns original string")
  void givenInvalidJson_whenProcess_thenReturnsOriginal(String path) {
    // Arrange
    ObfuscationTemplate template = new ObfuscationTemplate();
    template.setBodyFields(Set.of(path));
    String invalidJson = "text/plain";

    // Act
    String result = BodyObfuscator.process(template, invalidJson);

    // Assert
    assertEquals(invalidJson, result);
  }

  @CsvSource({
      "password"
  })
  @ParameterizedTest
  @DisplayName("Given root-level field longer than 6 chars, when process, then masks value")
  void givenRootLevelField_whenProcess_thenMasksValue(String path) {
    // Arrange
    ObfuscationTemplate template = new ObfuscationTemplate();
    template.setBodyFields(Set.of(path));
    String json = "{\"password\":\"secretValue\"}";

    // Act
    String result = BodyObfuscator.process(template, json);

    // Assert
    assertTrue(result.contains("\"password\":\"sec*****lue\""));
  }

  @ParameterizedTest(name = "# {index} – path={0}, original={2}, expected={3}")
  @CsvSource({
      "user.email,user@example.com,use*****com",
      "account.token,abcdef123456,abc*****456"
  })
  @DisplayName("Given nested field, when process, then masks nested value")
  void givenNestedField_whenProcess_thenMasks(String path,
                                              String originalValue,
                                              String expectedMasked) {
    //Arrange
    ObfuscationTemplate template = new ObfuscationTemplate();
    template.setBodyFields(Set.of(path));

    String parent = path.split(JSON_SEGMENT_SPLITTER_REGEX)[0];
    String field = path.split(JSON_SEGMENT_SPLITTER_REGEX)[1];
    String json = String.format("{\"%s\":{\"%s\":\"%s\"}}", parent, field, originalValue);

    //Act
    String result = BodyObfuscator.process(template, json);
    log.info(result);

    //Assert
    assertTrue(result.contains(String.format("\"%s\":\"%s\"", field, expectedMasked)));
  }

  @CsvSource({
      "items[*].code"
  })
  @ParameterizedTest
  @DisplayName("Given array wildcard path, when process, then masks all matching elements")
  void givenArrayWildcard_whenProcess_thenMasksAllElements(String path) {
    // Arrange
    ObfuscationTemplate template = new ObfuscationTemplate();
    template.setBodyFields(Set.of(path));
    String json = "{\"items\":[{\"code\":\"firstvalue\"},{\"code\":\"secondval\"}]}";

    // Act
    String result = BodyObfuscator.process(template, json);

    // Assert
    assertTrue(result.contains("\"code\":\"fir*****lue\""));
    assertTrue(result.contains("\"code\":\"sec*****val\""));
  }

  @CsvSource({
      "field"
  })
  @DisplayName("Given small string value <=6 chars, when process, then leaves value intact")
  void givenShortValue_whenProcess_thenLeavesIntact(String path) {
    // Arrange
    ObfuscationTemplate template = new ObfuscationTemplate();
    template.setBodyFields(Set.of(path));

    // Act
    String result = BodyObfuscator.process(template, JSON_SHORT_VALUES);

    // Assert
    assertTrue(result.contains(JSON_SHORT_VALUES));
  }
}
