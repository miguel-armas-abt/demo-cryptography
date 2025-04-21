package com.demo.poc.commons.core.logging;

import com.demo.poc.commons.core.logging.constants.RestLoggingConstant;
import com.demo.poc.commons.core.logging.utils.HeaderExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;

import org.springframework.web.server.ServerWebExchange;

import java.util.Map;
import java.util.Optional;

import static com.demo.poc.commons.core.logging.constants.RestLoggingConstant.BODY;
import static com.demo.poc.commons.core.logging.constants.RestLoggingConstant.HEADERS;
import static com.demo.poc.commons.core.logging.constants.RestLoggingConstant.METHOD;
import static com.demo.poc.commons.core.logging.constants.RestLoggingConstant.STATUS;
import static com.demo.poc.commons.core.logging.constants.RestLoggingConstant.URI;
import static com.demo.poc.commons.core.logging.enums.LoggingType.REST_CLIENT_REQ;
import static com.demo.poc.commons.core.logging.enums.LoggingType.REST_CLIENT_RES;
import static com.demo.poc.commons.core.logging.enums.LoggingType.REST_SERVER_REQ;
import static com.demo.poc.commons.core.logging.enums.LoggingType.REST_SERVER_RES;
import static com.demo.poc.commons.core.tracing.utils.TraceHeaderExtractor.extractTraceHeadersAsMap;

@Slf4j
@RequiredArgsConstructor
public class ThreadContextInjector {

  private static void putInContext(String key, String value) {
    ThreadContext.put(key, StringUtils.defaultString(value));
  }

  public void populateFromTraceHeaders(Map<String, String> traceHeaders) {
    traceHeaders.forEach(ThreadContextInjector::putInContext);
  }

  public void populateFromRestClientRequest(String method, String uri, Map<String, String> headers, String body) {
    populateFromRestRequest(REST_CLIENT_REQ.getCode(), method, uri, headers, body);
    log.info(REST_CLIENT_REQ.getMessage());
    ThreadContext.clearAll();
  }

  public void populateFromRestClientResponse(Map<String, String> headers, String uri, String body, String httpCode) {
    populateFromRestResponse(REST_CLIENT_RES.getCode(), uri, headers, body, httpCode);
    log.info(REST_CLIENT_RES.getMessage());
    ThreadContext.clearAll();
  }

  public void populateFromRestServerRequest(String method, String uri, Map<String, String> headers, String body) {
    populateFromRestRequest(REST_SERVER_REQ.getCode(), method, uri, headers, body);
    log.info(REST_SERVER_REQ.getMessage());
    ThreadContext.clearAll();
  }

  public void populateFromRestServerResponse(Map<String, String> headers, String uri, String body, String httpCode) {
    populateFromRestResponse(REST_SERVER_RES.getCode(), uri, headers, body, httpCode);
    log.info(REST_SERVER_RES.getMessage());
    ThreadContext.clearAll();
  }

  public void populateFromException(Throwable ex, ServerWebExchange exchange) {
    String message = Optional.ofNullable(ex.getMessage()).orElse("Undefined error message");

    populateFromTraceHeaders(extractTraceHeadersAsMap(exchange.getRequest().getHeaders()::getFirst));
    log.error(message, ex);
    ThreadContext.clearAll();
  }

  private void populateFromRestRequest(String prefix, String method, String uri, Map<String, String> headers, String body) {
    populateFromTraceHeaders(extractTraceHeadersAsMap(headers::get));
    putInContext(prefix + METHOD, method);
    putInContext(prefix + URI, uri);
    putInContext(prefix + HEADERS, HeaderExtractor.getHeadersAsString(headers));
    putInContext(prefix + BODY, body);
  }

  private void populateFromRestResponse(String prefix, String uri, Map<String, String> headers, String body, String httpCode) {
    populateFromTraceHeaders(extractTraceHeadersAsMap(headers::get));
    putInContext(prefix + HEADERS, HeaderExtractor.getHeadersAsString(headers));
    putInContext(prefix + RestLoggingConstant.URI, uri);
    putInContext(prefix + BODY, body);
    putInContext(prefix + STATUS, httpCode);
  }

}
