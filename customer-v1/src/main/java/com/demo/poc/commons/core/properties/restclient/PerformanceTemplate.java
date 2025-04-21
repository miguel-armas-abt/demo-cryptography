package com.demo.poc.commons.core.properties.restclient;

import com.demo.poc.commons.core.restclient.enums.ConcurrencyLevel;
import com.demo.poc.commons.core.restclient.enums.TimeoutLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerformanceTemplate {

  private TimeoutLevel timeout;
  private ConcurrencyLevel concurrency;
}