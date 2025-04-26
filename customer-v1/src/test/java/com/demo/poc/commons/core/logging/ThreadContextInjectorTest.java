package com.demo.poc.commons.core.logging;

import com.demo.poc.commons.core.logging.dto.RestRequestLog;
import com.demo.poc.commons.core.logging.dto.RestResponseLog;
import com.demo.poc.commons.core.logging.enums.LoggingType;
import com.demo.poc.commons.core.tracing.enums.TraceParam;
import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ThreadContextInjectorTest {

  private ThreadContextInjector injector;

  @BeforeEach
  void setUp() {
    injector = new ThreadContextInjector();
    ThreadContext.clearAll();
  }

  @AfterEach
  void tearDown() {
    ThreadContext.clearAll();
  }

  private static final String TRACE_PARENT = "00-aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa-bbbbbbbbbbbbbbbb-01";
  private static final String METHOD = "POST";
  private static final String URI = "http://example.com";
  private static final String BODY = "{}";
  private static final String HTTP_CODE = "200";

  @Test
  @DisplayName("Given headers map, when populateFromHeaders, then ThreadContext contains those entries")
  void givenHeadersMap_whenPopulateFromHeaders_thenContainsEntries() {
    // Arrange
    Map<String, String> headers = Map.of("h1", "v1", "h2", "v2");

    // Act
    injector.populateFromHeaders(headers);

    // Assert
    assertEquals("v1", ThreadContext.get("h1"));
    assertEquals("v2", ThreadContext.get("h2"));
  }

  @ParameterizedTest(name = "#{index} – loggingType={0}")
  @CsvSource({ "REST_CLIENT_REQ", "REST_SERVER_REQ" })
  @DisplayName("Given RestRequestLog, when populateFromRestRequest, then ThreadContext has trace and request info")
  void givenRestRequestLog_whenPopulateFromRestRequest_thenInjectsTraceAndRequest(String loggingTypeName) {
    // Arrange
    LoggingType type = LoggingType.valueOf(loggingTypeName);
    String traceParent = TRACE_PARENT;
    Map<String, String> traceHeaders = Map.of(
        TraceParam.TRACE_PARENT.getKey(), traceParent,
        TraceParam.TRACE_ID.getKey(), TraceParam.Util.getTraceId.apply(traceParent),
        TraceParam.SPAN_ID.getKey(), TraceParam.Util.getSpanId.apply(traceParent)
    );
    RestRequestLog log = RestRequestLog.builder()
        .traceParent(traceParent)
        .method(METHOD)
        .uri(URI)
        .requestHeaders(Map.of("channelId", "APP"))
        .requestBody(BODY)
        .build();

    // Act
    injector.populateFromRestRequest(type, log);

    // Assert
    // trace headers
    assertEquals(traceParent, ThreadContext.get(TraceParam.TRACE_PARENT.getKey()));
    assertEquals(traceHeaders.get(TraceParam.TRACE_ID.getKey()), ThreadContext.get(TraceParam.TRACE_ID.getKey()));
    assertEquals(traceHeaders.get(TraceParam.SPAN_ID.getKey()), ThreadContext.get(TraceParam.SPAN_ID.getKey()));

    // request fields
    String code = type.getCode();
    assertEquals(METHOD, ThreadContext.get(code + ".method"));
    assertEquals(URI, ThreadContext.get(code + ".uri"));
    assertEquals("channelId=APP", ThreadContext.get(code + ".headers"));
    assertEquals(BODY, ThreadContext.get(code + ".body"));
  }

  @ParameterizedTest(name = "#{index} – loggingType={0}")
  @CsvSource({ "REST_CLIENT_RES", "REST_SERVER_RES" })
  @DisplayName("Given RestResponseLog, when populateFromRestResponse, then ThreadContext has trace and response info")
  void givenRestResponseLog_whenPopulateFromRestResponse_thenInjectsTraceAndResponse(
      String loggingTypeName
  ) {
    // Arrange
    LoggingType type = LoggingType.valueOf(loggingTypeName);
    String traceParent = TRACE_PARENT;
    RestResponseLog log = RestResponseLog.builder()
        .traceParent(traceParent)
        .httpCode(HTTP_CODE)
        .uri(URI)
        .responseHeaders(Map.of("channelId", "APP"))
        .responseBody(BODY)
        .build();

    // Act
    injector.populateFromRestResponse(type, log);

    // Assert
    // trace headers
    assertEquals(traceParent, ThreadContext.get(TraceParam.TRACE_PARENT.getKey()));
    assertEquals(TraceParam.Util.getTraceId.apply(traceParent), ThreadContext.get(TraceParam.TRACE_ID.getKey()));
    assertEquals(TraceParam.Util.getSpanId.apply(traceParent), ThreadContext.get(TraceParam.SPAN_ID.getKey()));

    // response fields
    String code = type.getCode();
    assertEquals("channelId=APP", ThreadContext.get(code + ".headers"));
    assertEquals(URI, ThreadContext.get(code + ".uri"));
    assertEquals(BODY, ThreadContext.get(code + ".body"));
    assertEquals(HTTP_CODE, ThreadContext.get(code + ".status"));
  }
}