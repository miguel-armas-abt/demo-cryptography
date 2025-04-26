package com.demo.poc.commons.core.logging;

import com.demo.poc.commons.core.logging.dto.RestRequestLog;
import com.demo.poc.commons.core.logging.dto.RestResponseLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;

import static com.demo.poc.commons.core.logging.enums.LoggingType.REST_SERVER_REQ;
import static com.demo.poc.commons.core.logging.enums.LoggingType.REST_SERVER_RES;

@Slf4j
@RequiredArgsConstructor
public class RestServerThreadContextInjector {

  private final ThreadContextInjector injector;

  public void populateFromRestServerRequest(RestRequestLog restRequestLog) {
    injector.populateFromRestRequest(REST_SERVER_REQ, restRequestLog);
    log.info(REST_SERVER_REQ.getMessage());
    ThreadContext.clearAll();
  }

  public void populateFromRestServerResponse(RestResponseLog restResponseLog) {
    injector.populateFromRestResponse(REST_SERVER_RES, restResponseLog);
    log.info(REST_SERVER_RES.getMessage());
    ThreadContext.clearAll();
  }
}
