package com.demo.poc.commons.core.restclient.enums;

import java.time.Duration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TimeoutLevel {

  EXCELLENT(1000, 200, 1000),
  GOOD(1500, 200, 2000),
  POOR(2000, 200, 4000),
  BAD(2500, 200, 8000);

  private final long connectionTimeoutInMillis;
  private final long writeTimeoutInMillis;
  private final long readTimeoutInMillis;

  public Duration getConnectionTimeoutDuration() {
    return Duration.ofMillis(connectionTimeoutInMillis);
  }

  public Duration getWriteTimeoutDuration() {
    return Duration.ofMillis(writeTimeoutInMillis);
  }

  public Duration getReadTimeoutDuration() {
    return Duration.ofMillis(readTimeoutInMillis);
  }
}