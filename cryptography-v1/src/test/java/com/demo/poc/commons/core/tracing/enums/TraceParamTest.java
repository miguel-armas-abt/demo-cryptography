package com.demo.poc.commons.core.tracing.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class TraceParamTest {

  private static final Pattern TRACE_PARENT_PATTERN = Pattern.compile(TraceParam.Constants.TRACE_PARENT_REGEX, Pattern.CASE_INSENSITIVE);

  @ParameterizedTest(name = "#{index} – traceParent={0}")
  @CsvSource({
      "00-aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa-bbbbbbbbbbbbbbbb-01, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa, bbbbbbbbbbbbbbbb",
  })
  @DisplayName("Given valid traceParent, when getTraceId/getSpanId, then return correct parts")
  void givenValidTraceParent_whenGetTraceIdAndSpanId_thenReturnCorrectParts(String traceParent,
                                                                            String expectedTraceId,
                                                                            String expectedSpanId) {
    // Arrange

    // Act
    String actualTraceId = TraceParam.Util.getTraceId.apply(traceParent);
    String actualSpanId  = TraceParam.Util.getSpanId.apply(traceParent);

    // Assert
    assertEquals(expectedTraceId, actualTraceId);
    assertEquals(expectedSpanId, actualSpanId);
  }

  @ParameterizedTest(name = "#{index} – traceParent={0}")
  @CsvSource({
      "00-aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa-bbbbbbbbbbbbbbbb-01, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa, bbbbbbbbbbbbbbbb"
  })
  @DisplayName("Given valid traceParent, when getTraceHeadersAsMap, then returns map with parent, id and span")
  void givenValidTraceParent_when_getTraceHeadersAsMap_then_returnsCorrectMap(String traceParent,
                                                                              String expectedTraceId,
                                                                              String expectedSpanId) {
    // Arrange

    // Act
    Map<String, String> headers = TraceParam.Util.getTraceHeadersAsMap(traceParent);

    // Assert
    assertEquals(3, headers.size());
    assertEquals(traceParent, headers.get(TraceParam.TRACE_PARENT.getKey()));
    assertEquals(expectedTraceId, headers.get(TraceParam.TRACE_ID.getKey()));
    assertEquals(expectedSpanId, headers.get(TraceParam.SPAN_ID.getKey()));
  }

  @DisplayName("Given null traceParent, when getTraceHeadersAsMap, then returns empty map")
  @Test
  void givenNullTraceParent_whenGetTraceHeadersAsMap_thenReturnsEmptyMap() {
    // Arrange

    // Act
    Map<String, String> headers = TraceParam.Util.getTraceHeadersAsMap(null);

    // Assert
    assertTrue(headers.isEmpty());
  }

  @DisplayName("Given generated traceParent, then matches TRACE_PARENT_REGEX")
  @Test
  void givenNewTraceParent_whenGenerated_thenMatchesRegex() {
    //Arrange

    // Act
    String original = "00-680c67d2bf68b0c6e171c1925a857543-ffffffffffffffff-01";
    String newTraceParent   = TraceParam.TRACE_PARENT.getGenerator().apply(original);

    // Assert
    assertTrue(TRACE_PARENT_PATTERN.matcher(newTraceParent).matches());

    // Also ensure traceId unchanged
    String generatedTraceId = TraceParam.Util.getTraceId.apply(newTraceParent);
    assertEquals(TraceParam.Util.getTraceId.apply(original), generatedTraceId);
  }

  @DisplayName("GeneratedId64 produces 16 hex characters")
  @Test
  void generatedId64Produces16Hex() {
    // Act
    String id = TraceParam.Util.generateId64.get();

    // Assert
    assertEquals(16, id.length());
    assertTrue(id.matches("^[0-9a-fA-F]{16}$"), "must only contain hex digits");
  }

  @DisplayName("GeneratedId128 produces 32 hex characters")
  @Test
  void generatedId128Produces32Hex() {
    // Act
    String id = TraceParam.Util.generateId128.get();

    // Assert
    assertEquals(32, id.length());
    assertTrue(id.matches("^[0-9a-fA-F]{32}$"), "must only contain hex digits");
  }
}