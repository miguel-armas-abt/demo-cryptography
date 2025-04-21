package com.demo.poc.commons.core.validations.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import com.demo.poc.commons.core.errors.exceptions.ReflectiveParamAssignmentException;
import com.demo.poc.commons.core.errors.exceptions.ReflectiveParamMappingException;
import com.demo.poc.commons.core.validations.params.DefaultParams;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ParamReflectiveMapper {

  private static final Map<Class<?>, Function<String, ?>> fieldTypeConverters = new HashMap<>();

  static {
    fieldTypeConverters.put(Integer.class, Integer::parseInt);
    fieldTypeConverters.put(Double.class, Double::parseDouble);
    fieldTypeConverters.put(Long.class, Long::parseLong);
    fieldTypeConverters.put(Boolean.class, Boolean::parseBoolean);
    fieldTypeConverters.put(BigDecimal.class, BigDecimal::new);
  }

  public static <T extends DefaultParams> T mapParam(Map<String, String> params,
                                                     Class<T> paramClass,
                                                     boolean isFieldNameSensitiveCase) {
    try {
      Object paramObject = paramClass.getDeclaredConstructor().newInstance();
      populateObject(params, paramObject, isFieldNameSensitiveCase);
      return (T) paramObject;
    } catch (Exception ex) {
      throw new ReflectiveParamMappingException(ex.getMessage());
    }
  }

  private static void populateObject(Map<String, String> params,
                                     Object paramObject,
                                     boolean isFieldNameSensitiveCase) {

    Class<?> paramClass = paramObject.getClass();
    List<Field> fields = new ArrayList<>();

    while (paramClass != null) {
      fields.addAll(Arrays.asList(paramClass.getDeclaredFields()));
      paramClass = paramClass.getSuperclass();
    }

    Map<String, String> processedParams = isFieldNameSensitiveCase
        ? convertKeysToLowerCase.apply(params)
        : params;

    fields.stream()
        .peek(field -> field.setAccessible(true))
        .forEach(field -> {
          ParamName paramNameAnnotation = field.getAnnotation(ParamName.class);
          String paramName = Optional.ofNullable(paramNameAnnotation)
              .map(ParamName::value)
              .orElseGet(field::getName);

          if (isFieldNameSensitiveCase)
            paramName = paramName.toLowerCase();

          Optional.ofNullable(processedParams.get(paramName))
              .ifPresent(paramValue -> {
                try {
                  Object convertedValue = convertValue(field.getType(), paramValue);
                  field.set(paramObject, convertedValue);
                } catch (IllegalAccessException ex) {
                  throw new ReflectiveParamAssignmentException(ex.getMessage());
                }
              });
        });
  }

  private static final UnaryOperator<Map<String, String>> convertKeysToLowerCase = params ->
      params.entrySet().stream()
          .collect(Collectors.toMap(
              entry -> entry.getKey().toLowerCase(),
              Map.Entry::getValue
          ));

  private static Object convertValue(Class<?> fieldType, String value) {
    Function<String, ?> converter = fieldTypeConverters.get(fieldType);

    if (converter != null)
      return converter.apply(value);

    return value;
  }
}