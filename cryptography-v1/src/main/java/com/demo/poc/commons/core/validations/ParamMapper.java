package com.demo.poc.commons.core.validations;

import java.util.Map;

import jakarta.validation.constraints.NotNull;

public interface ParamMapper {

  Object map(@NotNull Map<String, String> params);

  boolean supports(Class<?> paramClass);
}
