package com.demo.poc.commons.core.serialization;

import com.demo.poc.commons.core.errors.exceptions.FileReadException;
import com.demo.poc.commons.core.errors.exceptions.JsonReadException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Slf4j
public class JsonSerializer {

  private final ObjectMapper objectMapper;

  public JsonSerializer(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
  }

  protected static InputStream getResourceAsStream(String filePath) {
    try {
      Resource resource = new ClassPathResource(filePath);
      return resource.getInputStream();
    } catch (IOException exception) {
      throw new FileReadException(exception.getMessage());
    }
  }

  protected <T> T readValue(InputStream inputStream, Class<T> objectClass) {
    try (InputStream is = inputStream) {
      return objectMapper.readValue(is, objectClass);
    } catch (IOException ex) {
      throw new JsonReadException(ex.getMessage());
    }
  }

  protected <T> List<T> readList(InputStream inputStream, Class<T> objectClass) {
    try (InputStream is = inputStream) {
      CollectionType collectionType =
          objectMapper.getTypeFactory().constructCollectionType(List.class, objectClass);
      return objectMapper.readValue(is, collectionType);
    } catch (IOException ex) {
      throw new JsonReadException(ex.getMessage());
    }
  }

  public <T> T readElementFromFile(String filePath, Class<T> objectClass) {
    return readValue(getResourceAsStream(filePath), objectClass);
  }

  public <T> List<T> readListFromFile(String filePath, Class<T> objectClass) {
    return readList(getResourceAsStream(filePath), objectClass);
  }

  public <T> Optional<T> readNullableObject(String jsonBody, Class<T> objectClass) {
    try {
      return Optional.ofNullable(objectMapper.readValue(jsonBody, objectClass));
    } catch (IOException ex) {
      log.warn("Json parsing error: {}", ex.getMessage());
      return Optional.empty();
    }
  }
}