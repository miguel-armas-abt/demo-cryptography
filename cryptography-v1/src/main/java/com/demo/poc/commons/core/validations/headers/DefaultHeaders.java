package com.demo.poc.commons.core.validations.headers;

import com.demo.poc.commons.core.tracing.enums.ForwardedParam;
import com.demo.poc.commons.core.tracing.enums.TraceParam;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultHeaders implements Serializable {

  @Pattern(regexp = ForwardedParam.Constants.CHANNEL_ID_REGEX)
  @NotEmpty
  private String channelId;

  @Pattern(regexp = TraceParam.Constants.TRACE_PARENT_REGEX)
  @NotEmpty
  private String traceParent;
}