package com.demo.service.entrypoint.encryption.params;

import com.demo.commons.tracing.enums.ForwardedParam;
import com.demo.commons.tracing.enums.TraceParam;
import com.demo.commons.validations.ParamMapper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
public class EncryptionHeaderMapper implements ParamMapper<EncryptionHeader> {

    private static final String FEATURE_KEY = "feature";
    private static final String ENCRYPTION_METHOD_KEY = "encryption-method";

    @Override
    public Map.Entry<EncryptionHeader, Map<String, String>> map(Map<String, String> params) {
        EncryptionHeader headers = new EncryptionHeader();
        headers.setTraceParent(params.get(TraceParam.TRACE_PARENT.getKey()));
        headers.setChannelId(params.get(ForwardedParam.CHANNEL_ID.getKey()));
        headers.setFeature(params.get(FEATURE_KEY));
        headers.setEncryptionMethod(params.get(ENCRYPTION_METHOD_KEY));

        Map<String, String> headersMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        headersMap.put(TraceParam.TRACE_PARENT.getKey(), headers.getTraceParent());
        headersMap.put(ForwardedParam.CHANNEL_ID.getKey(), headers.getChannelId());
        headersMap.put(FEATURE_KEY, headers.getFeature());
        headersMap.put(ENCRYPTION_METHOD_KEY, headers.getEncryptionMethod());

        return Map.entry(headers, headersMap);
    }

    @Override
    public boolean supports(Class<?> paramClass) {
        return EncryptionHeader.class.isAssignableFrom(paramClass);
    }
}
