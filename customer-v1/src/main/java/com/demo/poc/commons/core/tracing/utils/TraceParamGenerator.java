package com.demo.poc.commons.core.tracing.utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TraceParamGenerator {

  public static final String DEFAULT_DATE_PATTERN = "yyyyMMddHHmmssSSS";
  public static final int PARENT_ID_SIZE = 16;
  public static final int TRACE_ID_SIZE = 32;

  public static final UnaryOperator<String> formatDate = pattern ->
      LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));

  public static String getTrace(int size) {
    StringBuilder traceState = new StringBuilder();
    SecureRandom random = new SecureRandom();
    byte[] randomBytes = new byte[(size / 2)];
    random.nextBytes(randomBytes);
    for (byte randomByte : randomBytes) {
      String hex = Integer.toHexString(randomByte + 128);
      if (hex.length() == 1) {
        hex = "0" + hex;
      }
      traceState.append(hex);
    }
    return traceState.toString();
  }


}
