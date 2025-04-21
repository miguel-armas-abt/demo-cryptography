package com.demo.poc.commons.core.tracing.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.demo.poc.commons.core.tracing.utils.TraceParamGenerator.DEFAULT_DATE_PATTERN;
import static com.demo.poc.commons.core.tracing.utils.TraceParamGenerator.PARENT_ID_SIZE;
import static com.demo.poc.commons.core.tracing.utils.TraceParamGenerator.TRACE_ID_SIZE;
import static com.demo.poc.commons.core.tracing.utils.TraceParamGenerator.formatDate;
import static com.demo.poc.commons.core.tracing.utils.TraceParamGenerator.getTrace;

@Getter
@RequiredArgsConstructor
public enum TraceParamType {

  TIMESTAMP("timestamp", () -> formatDate.apply(DEFAULT_DATE_PATTERN)),
  TRACE_ID("trace-id", () -> getTrace(TRACE_ID_SIZE)),
  PARENT_ID("parent-id", () -> getTrace(PARENT_ID_SIZE));

  private final String key;
  private final ParamGenerator paramGenerator;

  @FunctionalInterface
  public interface ParamGenerator {
    String generateParam();
  }
}
