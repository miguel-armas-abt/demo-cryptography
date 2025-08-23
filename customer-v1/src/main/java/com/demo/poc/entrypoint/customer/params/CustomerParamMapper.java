package com.demo.poc.entrypoint.customer.params;

import com.demo.poc.commons.core.validations.ParamMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomerParamMapper implements ParamMapper {

    @Override
    public Object map(Map<String, String> params) {
        return CustomerParam.builder()
                .documentType(params.get("documentType"))
                .build();
    }

    @Override
    public boolean supports(Class<?> paramClass) {
        return CustomerParam.class.isAssignableFrom(paramClass);
    }
}
