package com.demo.poc.commons.core.logging;

import com.demo.poc.commons.core.constants.Symbol;
import com.demo.poc.commons.core.logging.dto.RestRequestLog;
import com.demo.poc.commons.core.logging.dto.RestResponseLog;
import com.demo.poc.commons.core.logging.enums.LoggingType;
import com.demo.poc.commons.core.tracing.enums.TraceParam;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class ThreadContextInjector {

  private static void putInContext(String key, String value) {
    ThreadContext.put(key, StringUtils.defaultString(value));
  }

  public void populateFromHeaders(Map<String, String> headers) {
    headers.forEach(ThreadContextInjector::putInContext);
  }

  public void populateFromRestRequest(LoggingType loggingType, RestRequestLog restRequestLog) {
    Map<String, String> traceHeaders = TraceParam.Util.getTraceHeadersAsMap(restRequestLog.getTraceParent());
    populateFromHeaders(traceHeaders);
    putInContext(loggingType.getCode() + RestConstants.METHOD, restRequestLog.getMethod());
    putInContext(loggingType.getCode() + RestConstants.URI, restRequestLog.getUri());
    putInContext(loggingType.getCode() + RestConstants.HEADERS, Utils.getHeadersAsString(restRequestLog.getRequestHeaders()));
    putInContext(loggingType.getCode() + RestConstants.BODY, restRequestLog.getRequestBody());
  }

  public void populateFromRestResponse(LoggingType loggingType, RestResponseLog restResponseLog) {
    Map<String, String> traceHeaders = TraceParam.Util.getTraceHeadersAsMap(restResponseLog.getTraceParent());
    populateFromHeaders(traceHeaders);
    putInContext(loggingType.getCode() + RestConstants.HEADERS, Utils.getHeadersAsString(restResponseLog.getResponseHeaders()));
    putInContext(loggingType.getCode() + RestConstants.URI, restResponseLog.getUri());
    putInContext(loggingType.getCode() + RestConstants.BODY, restResponseLog.getResponseBody());
    putInContext(loggingType.getCode() + RestConstants.STATUS, restResponseLog.getHttpCode());
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Utils {

    public static String getHeadersAsString(Map<String, String> headers) {
      return headers.entrySet().stream()
          .map(entry -> entry.getKey() + Symbol.EQUAL + entry.getValue())
          .collect(Collectors.joining(Symbol.COMMA));
    }
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class RestConstants {

    public static final String METHOD = ".method";
    public static final String URI = ".uri";
    public static final String HEADERS = ".headers";
    public static final String BODY = ".body";
    public static final String STATUS = ".status";

  }

}
