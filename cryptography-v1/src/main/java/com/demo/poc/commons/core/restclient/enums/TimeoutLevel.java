package com.demo.poc.commons.core.restclient.enums;

import lombok.RequiredArgsConstructor;

import java.time.Duration;

@RequiredArgsConstructor
public enum TimeoutLevel {

  EXCELLENT(1000,  200,   1000),
  GOOD     (1250,  200,   2000),
  AVERAGE  (1500,  200,   3000),
  POOR     (1750,  200,   4000),
  BAD      (2000,  200,   8000),
  CRITICAL (2500,  200,  12000),
  FATAL    (2750,  200, 24000);

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