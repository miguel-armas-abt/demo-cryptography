package com.demo.poc.encryption.params;

import com.demo.poc.commons.core.validations.ParamMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EncryptionHeaderMapper implements ParamMapper {

    @Override
    public Object map(Map<String, String> params) {
        EncryptionHeader headers = new EncryptionHeader();
        headers.setTraceId(params.get("trace-id"));
        headers.setChannelId(params.get("channel-id"));
        headers.setFeature(params.get("feature"));
        headers.setEncryptionMethod(params.get("encryption-method"));

        return headers;
    }

    @Override
    public boolean supports(Class<?> paramClass) {
        return EncryptionHeader.class.isAssignableFrom(paramClass);
    }
}
