package com.demo.poc.commons.core.validations;

import com.demo.poc.commons.core.errors.exceptions.InvalidFieldException;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.DisplayName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static org.junit.jupiter.api.Assertions.*;

class BodyValidatorTest {

  private final BodyValidator validator = new BodyValidator();

  @AllArgsConstructor
  @Getter
  @Setter
  public static class DummyDto {
    @NotBlank
    private final String field;
  }

  @ParameterizedTest(name = "#{index} – field=''{0}'' → isValid={1}")
  @CsvSource(value = {
      "hello,true",
      "'',false",
      "NULL,false"
  }, nullValues = "NULL")
  @DisplayName("Given DummyDto, when isValid, then returns expected boolean")
  void givenDummyDtoWhenIsValid_thenReturnsExpected(String fieldValue, boolean expected) {
    // Arrange
    DummyDto dto = new DummyDto(fieldValue);

    // Act
    boolean actual = validator.isValid(dto);

    // Assert
    assertEquals(expected, actual);
  }

  @ParameterizedTest(name = "#{index} – field=''{0}'' → shouldError={1}")
  @CsvSource(value = {
      "hello,false",
      "'',true",
      "NULL,true"
  }, nullValues = "NULL")
  @DisplayName("Given DummyDto, when validate, then throws or not according to validation")
  void givenDummyDto_whenValidate_thenThrowsOrNot(String fieldValue, boolean shouldError) {
    // Arrange
    DummyDto dto = new DummyDto(fieldValue);

    // Act & Assert
    if (!shouldError) {
      assertDoesNotThrow(() -> validator.validate(dto));
    } else {
      InvalidFieldException ex = assertThrows(InvalidFieldException.class,
          () -> validator.validate(dto));
      assertTrue(ex.getMessage().contains("The field 'field'"));
    }
  }
}
