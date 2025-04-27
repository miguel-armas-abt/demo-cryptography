package com.demo.poc.commons.core.logging.obfuscation.header;

import com.demo.poc.commons.core.constants.Symbol;
import com.demo.poc.commons.core.properties.logging.ObfuscationTemplate;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.demo.poc.commons.core.logging.obfuscation.constants.ObfuscationConstant.OBFUSCATION_MASK;
import static com.demo.poc.commons.core.logging.obfuscation.constants.ObfuscationConstant.NULL_WARNING;

public class HeaderObfuscator {

    public static String process(ObfuscationTemplate obfuscation,
                                 Map<String, String> headers) {

        Set<String> headerFields = Optional.ofNullable(obfuscation)
            .filter(template -> Objects.nonNull(template.getHeaders()))
            .map(ObfuscationTemplate::getHeaders)
            .orElse(Set.of());

        return headers
            .entrySet()
            .stream()
            .map(entry -> obfuscateHeader(entry, headerFields))
            .collect(Collectors.joining(Symbol.COMMA_WITH_SPACE));
    }

    private static String obfuscateHeader(Map.Entry<String, String> header,
                                          Set<String> sensitiveHeaders) {
        String key = header.getKey();
        return !sensitiveHeaders.contains(key)
            ? key + Symbol.EQUAL + header.getValue()
            : Optional.ofNullable(header.getValue())
                .map(value -> key + Symbol.EQUAL + partiallyObfuscate(value))
                .orElse(key + Symbol.EQUAL + NULL_WARNING);
    }

    private static String partiallyObfuscate(String value) {
        return value.length() <= 6
            ? OBFUSCATION_MASK
            : value.substring(0, 3) + OBFUSCATION_MASK + value.substring(value.length() - 3);
    }
}