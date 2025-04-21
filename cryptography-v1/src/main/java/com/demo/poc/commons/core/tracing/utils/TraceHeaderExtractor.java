package com.demo.poc.commons.core.tracing.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import com.demo.poc.commons.core.tracing.enums.TraceParamType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TraceHeaderExtractor {

  public static Map<String, String> extractTraceHeadersAsMap(UnaryOperator<String> headerProvider) {
    return Arrays.stream(TraceParamType.values())
        .distinct()
        .map(TraceParamType::getKey)
        .map(headerName -> Map.entry(headerName, Optional.ofNullable(headerProvider.apply(headerName))))
        .filter(entry -> entry.getValue().isPresent())
        .collect(Collectors.toMap(entry -> toCamelCase(entry.getKey()), entry -> entry.getValue().get()));
  }

  private static String toCamelCase(String value) {
    String[] parts = value.split("-");
    return parts[0] + Arrays.stream(parts, 1, parts.length)
        .map(part -> part.substring(0, 1).toUpperCase() + part.substring(1))
        .collect(Collectors.joining());
  }
}
