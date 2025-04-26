package com.demo.poc.commons.core.validations.headers;

import java.util.Map;

import com.demo.poc.commons.core.tracing.enums.ForwardedParam;
import com.demo.poc.commons.core.tracing.enums.TraceParam;
import com.demo.poc.commons.core.validations.ParamMapper;

public class DefaultHeadersMapper implements ParamMapper {

  @Override
  public Object map(Map<String, String> params) {
    return DefaultHeaders.builder()
        .channelId(params.get(ForwardedParam.CHANNEL_ID.getKey()))
        .traceParent(params.get(TraceParam.TRACE_PARENT.getKey()))
        .build();
  }

  @Override
  public boolean supports(Class<?> paramClass) {
    return DefaultHeaders.class.isAssignableFrom(paramClass);
  }
}
