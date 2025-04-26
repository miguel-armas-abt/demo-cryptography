package com.demo.poc.commons.core.tracing.enums;

import java.security.SecureRandom;
import java.util.HexFormat;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import com.demo.poc.commons.core.constants.Symbol;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@RequiredArgsConstructor
public enum TraceParam {

  TRACE_PARENT("traceParent", Util.getNewTraceParent),
  TRACE_ID("traceId", Util.getTraceId),
  SPAN_ID("spanId", Util.getSpanId);

  private final String key;
  private final UnaryOperator<String> generator;

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class Util {

    public static final Supplier<String> generateId64 = () -> {
      byte[] bytes = new byte[8];
      new SecureRandom().nextBytes(bytes);
      return HexFormat.of().formatHex(bytes);
    };

    public static final Supplier<String> generateId128 = () ->
        UUID.randomUUID().toString().replaceAll(Symbol.MIDDLE_DASH, StringUtils.EMPTY);

    public static final UnaryOperator<String> getTraceId = traceParent ->
        traceParent.split(Symbol.MIDDLE_DASH)[1];

    public static final UnaryOperator<String> getSpanId = traceParent ->
        traceParent.split(Symbol.MIDDLE_DASH)[2];

    public static final UnaryOperator<String> getNewTraceParent = traceParent -> {
      String traceId = getTraceId.apply(traceParent);
      String newSpanId = generateId64.get();
      return String.format("00-%s-%s-01", traceId, newSpanId);
    };

    public static Map<String, String> getTraceHeadersAsMap(String traceParent) {
      return Optional.ofNullable(traceParent)
          .map(trace -> {
            String traceId = Util.getTraceId.apply(trace);
            String spanId = Util.getSpanId.apply(trace);
            Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            headers.put(TraceParam.TRACE_PARENT.getKey(), trace);
            headers.put(TraceParam.TRACE_ID.getKey(), traceId);
            headers.put(TraceParam.SPAN_ID.getKey(), spanId);

            return headers;
          })
          .orElse(Map.of());
    }
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class Constants {
    public static final String TRACE_PARENT_REGEX = "^[0-9A-Fa-f]{2}-[0-9A-Fa-f]{32}-[0-9A-Fa-f]{16}-[0-9A-Fa-f]{2}$";
  }

}
