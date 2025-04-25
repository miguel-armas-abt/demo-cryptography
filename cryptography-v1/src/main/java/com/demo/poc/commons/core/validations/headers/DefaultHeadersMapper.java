package com.demo.poc.commons.core.validations.headers;

import java.util.Map;

import com.demo.poc.commons.core.validations.ParamMapper;

public class DefaultHeadersMapper implements ParamMapper {

  @Override
  public Object map(Map<String, String> params) {
    return DefaultHeaders.builder()
        .channelId(params.get("channel-id"))
        .traceId(params.get("trace-id"))
        .build();
  }

  @Override
  public boolean supports(Class<?> paramClass) {
    return DefaultHeaders.class.isAssignableFrom(paramClass);
  }
}
