package com.demo.poc.commons.core.tracing.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ForwardedParam {

  CHANNEL_ID("channelId");

  private final String key;

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {
    public static final String CHANNEL_ID_REGEX = "^(web|app|WEB|APP)$";
  }
}
