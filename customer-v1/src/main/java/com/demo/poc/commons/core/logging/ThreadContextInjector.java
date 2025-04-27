package com.demo.poc.commons.core.logging;

import com.demo.poc.commons.core.constants.Symbol;
import com.demo.poc.commons.core.logging.dto.RestRequestLog;
import com.demo.poc.commons.core.logging.dto.RestResponseLog;
import com.demo.poc.commons.core.logging.enums.LoggingType;
import com.demo.poc.commons.core.logging.obfuscation.body.BodyObfuscator;
import com.demo.poc.commons.core.logging.obfuscation.header.HeaderObfuscator;
import com.demo.poc.commons.core.properties.logging.ObfuscationTemplate;
import com.demo.poc.commons.core.tracing.enums.TraceParam;
import com.demo.poc.commons.custom.properties.ApplicationProperties;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ThreadContextInjector {

  private final ObfuscationTemplate obfuscation;

  public ThreadContextInjector(ApplicationProperties properties) {
    this.obfuscation = properties.searchObfuscation();
  }

  private static void putInContext(String key, String value) {
    ThreadContext.put(key, StringUtils.defaultString(value));
  }

  public void populateFromHeaders(Map<String, String> headers) {
    headers.forEach(ThreadContextInjector::putInContext);
  }

  public void populateFromRestRequest(LoggingType loggingType, RestRequestLog restRequestLog) {
    ThreadContext.clearAll();
    Map<String, String> traceHeaders = TraceParam.Util.getTraceHeadersAsMap(restRequestLog.getTraceParent());
    populateFromHeaders(traceHeaders);
    putInContext(loggingType.getCode() + RestConstants.METHOD, restRequestLog.getMethod());
    putInContext(loggingType.getCode() + RestConstants.URI, restRequestLog.getUri());
    putInContext(loggingType.getCode() + RestConstants.HEADERS, HeaderObfuscator.process(obfuscation, restRequestLog.getRequestHeaders()));
    putInContext(loggingType.getCode() + RestConstants.BODY, BodyObfuscator.process(obfuscation, restRequestLog.getRequestBody()));
    log.info(loggingType.getMessage());
  }

  public void populateFromRestResponse(LoggingType loggingType, RestResponseLog restResponseLog) {
    ThreadContext.clearAll();
    Map<String, String> traceHeaders = TraceParam.Util.getTraceHeadersAsMap(restResponseLog.getTraceParent());
    populateFromHeaders(traceHeaders);
    putInContext(loggingType.getCode() + RestConstants.HEADERS, HeaderObfuscator.process(obfuscation, restResponseLog.getResponseHeaders()));
    putInContext(loggingType.getCode() + RestConstants.URI, restResponseLog.getUri());
    putInContext(loggingType.getCode() + RestConstants.BODY, BodyObfuscator.process(obfuscation, restResponseLog.getResponseBody()));
    putInContext(loggingType.getCode() + RestConstants.STATUS, restResponseLog.getHttpCode());
    log.info(loggingType.getMessage());
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
