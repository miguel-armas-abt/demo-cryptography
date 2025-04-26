package com.demo.poc.commons.core.restserver.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestServerUtils {

  public static Map<String, String> extractHeadersAsMap(HttpServletRequest req) {
    var headerNames = Collections.list(req.getHeaderNames());
    Map<String,String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    headerNames.forEach(name -> map.put(name, req.getHeader(name)));
    return map;
  }

  public static Map<String, String> extractQueryParamsAsMap(HttpServletRequest request) {
    return Optional.ofNullable(request.getParameterNames())
        .map(Collections::list)
        .orElse(new ArrayList<>())
        .stream()
        .collect(Collectors.toMap(
            paramName -> paramName,
            request::getParameter
        ));
  }

}
