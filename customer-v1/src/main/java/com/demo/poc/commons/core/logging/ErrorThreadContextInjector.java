package com.demo.poc.commons.core.logging;

import java.util.Map;

import com.demo.poc.commons.core.tracing.enums.TraceParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;

import org.springframework.web.context.request.WebRequest;

@Slf4j
@RequiredArgsConstructor
public class ErrorThreadContextInjector {

  private final ThreadContextInjector injector;

  public void populateFromException(Throwable ex, WebRequest request) {
    ThreadContext.clearAll();
    String traceParent = request.getHeader(TraceParam.TRACE_PARENT.getKey().toLowerCase());
    Map<String, String> traceHeaders = TraceParam.Util.getTraceHeadersAsMap(traceParent);
    injector.populateFromHeaders(traceHeaders);
    log.error(ex.getMessage(), ex);
  }
}
