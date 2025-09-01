package com.demo.service.entrypoint.customer.params;

import com.demo.commons.validations.ParamMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomerParamMapper implements ParamMapper<CustomerParam> {

    private static final String DOCUMENT_TYPE_KEY = "documentType";

    @Override
    public Map.Entry<CustomerParam, Map<String, String>> map(Map<String, String> params) {
        CustomerParam customerParam = CustomerParam.builder()
                .documentType(params.get(DOCUMENT_TYPE_KEY))
                .build();

        Map<String, String> customerParamMap = new HashMap<>();
        customerParamMap.put(DOCUMENT_TYPE_KEY, customerParam.getDocumentType());

        return Map.entry(customerParam, customerParamMap);
    }

    @Override
    public boolean supports(Class<?> paramClass) {
        return CustomerParam.class.isAssignableFrom(paramClass);
    }
}
