package com.demo.poc.commons.core.logging;

import com.demo.poc.commons.core.logging.dto.RestRequestLog;
import com.demo.poc.commons.core.logging.dto.RestResponseLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;

import static com.demo.poc.commons.core.logging.enums.LoggingType.REST_CLIENT_REQ;
import static com.demo.poc.commons.core.logging.enums.LoggingType.REST_CLIENT_RES;

@Slf4j
@RequiredArgsConstructor
public class RestClientThreadContextInjector {

  private final ThreadContextInjector injector;

  public void populateRequest(RestRequestLog restRequestLog) {
    injector.populateFromRestRequest(REST_CLIENT_REQ, restRequestLog);
    log.info(REST_CLIENT_REQ.getMessage());
    ThreadContext.clearAll();
  }

  public void populateResponse(RestResponseLog restResponseLog) {
    injector.populateFromRestResponse(REST_CLIENT_RES, restResponseLog);
    log.info(REST_CLIENT_RES.getMessage());
    ThreadContext.clearAll();
  }
}
