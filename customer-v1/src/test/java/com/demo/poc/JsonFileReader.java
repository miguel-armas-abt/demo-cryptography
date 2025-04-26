package com.demo.poc;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonFileReader {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static <T> T readObjectFromFile(Class<T> type, String filePath) {
    try {
      InputStream inputStream = JsonFileReader.class.getClassLoader().getResourceAsStream(filePath);
      return objectMapper.readValue(inputStream, type);
    } catch (IOException ioException) {
      throw new IllegalArgumentException("Error reading json object from file", ioException);
    }
  }

  public static <T> List<T> readListFromFile(Class<T> type, String filePath) {
    try {
      InputStream inputStream = JsonFileReader.class.getClassLoader().getResourceAsStream(filePath);
      return objectMapper.readValue(inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, type));
    } catch (IOException ioException) {
      throw new IllegalArgumentException("Error reading json list from file", ioException);
    }
  }

}
