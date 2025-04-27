package com.demo.poc.commons.core.serialization;

import com.demo.poc.commons.core.errors.exceptions.JsonReadException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonSerializerTest {

  @NoArgsConstructor
  @Setter
  @Getter
  public static class DummyDto {
    private String name;
    private int value;
  }

  @ParameterizedTest(name = "#{index} – json={0}, name={1}, value={2}")
  @CsvSource({
      "'{\"name\":\"foo\",\"value\":1}', foo, 1",
      "'{\"name\":\"bar\",\"value\":2}', bar, 2"
  })
  @DisplayName("Given valid JSON string, when readValue, then returns correct object")
  void givenValidJsonString_whenReadValue_thenReturnsCorrectObject(String json,
                                                                   String expectedName,
                                                                   int expectedValue) {
    // Arrange
    JsonSerializer serializer = new JsonSerializer(new ObjectMapper());
    InputStream inputStream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));

    // Act
    DummyDto dto = serializer.readValue(inputStream, DummyDto.class);

    // Assert
    assertEquals(expectedName, dto.getName());
    assertEquals(expectedValue, dto.getValue());
  }

  @ParameterizedTest(name = "#{index} – jsonList={0} → [({1};{2}) , ({3};{4})]")
  @CsvSource({
      "'[{\"name\":\"a\",\"value\":10},{\"name\":\"b\",\"value\":20}]', a, 10, b, 20",
      "'[{\"name\":\"x\",\"value\":100},{\"name\":\"y\",\"value\":200}]', x, 100, y, 200"
  })
  @DisplayName("Given valid JSON array string, when readList, then returns correct list")
  void givenValidJsonArrayString_when_readList_then_returnsCorrectList(String jsonList,
                                                                       String firstName, int firstValue,
                                                                       String secondName, int secondValue) {
    // Arrange
    JsonSerializer serializer = new JsonSerializer(new ObjectMapper());
    InputStream is = new ByteArrayInputStream(jsonList.getBytes(StandardCharsets.UTF_8));

    // Act
    List<DummyDto> list = serializer.readList(is, DummyDto.class);

    // Assert
    assertEquals(2, list.size());
    assertEquals(firstName, list.get(0).getName());
    assertEquals(firstValue, list.get(0).getValue());
    assertEquals(secondName, list.get(1).getName());
    assertEquals(secondValue, list.get(1).getValue());
  }

  @ParameterizedTest(name = "#{index} – badJson={0}")
  @CsvSource({
      "text-plain",
      "{\"name\"::123}"
  })
  @DisplayName("Given invalid JSON string, when readValue, then throws JsonReadException")
  void givenInvalidJsonString_whenReadValue_thenThrowsJsonReadException(String badJson) {
    // Arrange
    JsonSerializer serializer = new JsonSerializer(new ObjectMapper());
    InputStream inputStream = new ByteArrayInputStream(badJson.getBytes(StandardCharsets.UTF_8));

    // Act & Assert
    JsonReadException ex = assertThrows(JsonReadException.class, () -> serializer.readValue(inputStream, DummyDto.class));
  }
}