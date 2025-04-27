package com.demo.poc.commons.core.logging.obfuscation.body;

import com.demo.poc.commons.core.properties.logging.ObfuscationTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import static com.demo.poc.commons.core.logging.obfuscation.constants.ObfuscationConstant.ARRAY_WILDCARD;
import static com.demo.poc.commons.core.logging.obfuscation.constants.ObfuscationConstant.JSON_SEGMENT_SPLITTER_REGEX;
import static com.demo.poc.commons.core.logging.obfuscation.constants.ObfuscationConstant.OBFUSCATION_MASK;

@Slf4j
public class BodyObfuscator {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String process(ObfuscationTemplate obfuscation, String jsonBody) {
        Set<String> bodyFields = Optional.ofNullable(obfuscation)
            .filter(template -> Objects.nonNull(template.getBodyFields()))
            .map(ObfuscationTemplate::getBodyFields)
            .orElse(Set.of());

        if (StringUtils.isEmpty(jsonBody) || bodyFields.isEmpty())
            return jsonBody;

        try {
            JsonNode jsonNode = OBJECT_MAPPER.readTree(jsonBody);

            bodyFields.forEach(fieldPath -> {
                String[] fieldPathSegments = fieldPath.split(JSON_SEGMENT_SPLITTER_REGEX);
                int incrementalIndex = 0;
                obfuscateFieldRecursively(jsonNode, fieldPathSegments, incrementalIndex);
            });

            return OBJECT_MAPPER.writeValueAsString(jsonNode);
        } catch (Exception e) {
            log.warn("Failed to obfuscate json {}", e.getMessage());
            return jsonBody;
        }
    }

    private static void obfuscateFieldRecursively(JsonNode jsonNode, String[] fieldPathSegments, int index) {
        if (wereAllFieldsProcessed(fieldPathSegments, index)) return;

        String segment = fieldPathSegments[index];

        if (segment.contains(ARRAY_WILDCARD)) {
            String arrayKey = segment.substring(0, segment.indexOf('['));
            JsonNode arrayNode = jsonNode.get(arrayKey);
            processArray(arrayNode, fieldPathSegments, index);
        } else {
            processObject(jsonNode, segment, fieldPathSegments, index);
        }
    }

    private static boolean wereAllFieldsProcessed(String[] fieldPathSegments, int index) {
        return index >= fieldPathSegments.length;
    }

    private static void processArray(JsonNode arrayNode, String[] fieldPathSegments, int index) {
        Optional.ofNullable(arrayNode).ifPresent(array -> IntStream.range(0, array.size())
            .mapToObj(array::get)
            .filter(JsonNode::isObject)
            .forEach(jsonObject -> obfuscateFieldRecursively(jsonObject, fieldPathSegments, index + 1)));
    }

    private static void processObject(JsonNode jsonNode, String segment, String[] fieldPathSegments, int index) {
        if (wasLastSegment(fieldPathSegments, index)) {
            obfuscateTargetField(jsonNode, segment);
        } else if (jsonNode.has(segment) && jsonNode.get(segment).isObject()) {
            obfuscateFieldRecursively(jsonNode.get(segment), fieldPathSegments, index + 1);
        }
    }

    private static boolean wasLastSegment(String[] fieldPathSegments, int index) {
        return index == fieldPathSegments.length - 1;
    }

    private static void obfuscateTargetField(JsonNode jsonNode, String field) {
        if (jsonNode.has(field) && jsonNode.get(field).isTextual()) {
            ((ObjectNode) jsonNode).put(field, partiallyObfuscate(jsonNode.get(field).asText()));
        }
    }

    private static String partiallyObfuscate(String value) {
        return value.length() > 6
            ? value.substring(0, 3) + OBFUSCATION_MASK + value.substring(value.length() - 3)
            : value;
    }
}
