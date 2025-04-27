package com.demo.poc.commons.core.restclient.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@Getter
@RequiredArgsConstructor
public enum ConcurrencyLevel {

  HIGH(50, 100, 5000),
  MEDIUM(30, 60, 4000),
  LOW(20, 40, 3000),
  MINIMAL(10, 20, 2000);

  private final int maxConnections;
  private final int queueSize;
  private final long queueTimeoutMillis;

  public Duration getQueueTimeoutDuration() {
    return Duration.ofMillis(queueTimeoutMillis);
  }
}
