package com.demo.poc.commons.core.validations.headers;

import java.io.Serializable;

import com.demo.poc.commons.core.validations.params.DefaultParams;
import com.demo.poc.commons.core.validations.utils.ParamName;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultHeaders extends DefaultParams implements Serializable {

  @Pattern(regexp = "^(web|app|WEB|APP)$")
  @NotEmpty
  @ParamName("channel-id")
  private String channelId;

  @NotEmpty
  @ParamName("trace-id")
  private String traceId;
}