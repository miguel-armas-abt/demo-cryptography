package com.demo.poc.commons.core.restclient.error;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.demo.poc.commons.core.serialization.JsonSerializer;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class RestClientErrorMapper {

  private final JsonSerializer jsonSerializer;

  public  <T> Optional<Map.Entry<String,String>> getCodeAndMessage(String jsonBody,
                                                                   Class<T> objectClass,
                                                                   Function<T, Map.Entry<String,String>> mapper) {
    return jsonSerializer.readNullableObject(jsonBody, objectClass)
        .map(mapper)
        .filter(codeAndMessage -> StringUtils.isNoneEmpty(codeAndMessage.getKey(), codeAndMessage.getValue()));
  }
}
