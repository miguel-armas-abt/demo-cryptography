package com.demo.poc.commons.core.logging.filter;

import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class LokiAvailabilityFilter extends Filter<Object> {

  private static final String LOKI_URL = "http://localhost:3100/loki/api/v1/push";

  @Override
  public FilterReply decide(Object event) {
    return isLokiAvailable() ? FilterReply.ACCEPT : FilterReply.DENY;
  }

  private boolean isLokiAvailable() {
    try {
      URL url = new URL(LOKI_URL);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("HEAD");
      return !isError(connection.getResponseCode());
    } catch (IOException e) {
      return false;
    }
  }

  public boolean isError(int httpCode) {
    int hundreds = httpCode/100;
    return hundreds == 5;
  }
}