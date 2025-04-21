package com.demo.poc.customer.repository.cryptography;

import com.demo.poc.commons.core.properties.restclient.RestClient;
import com.demo.poc.commons.core.restclient.RestClientTemplate;
import com.demo.poc.commons.core.restclient.dto.ExchangeRequest;
import com.demo.poc.commons.core.restclient.utils.HeadersFiller;
import com.demo.poc.commons.custom.properties.ApplicationProperties;
import com.demo.poc.customer.repository.cryptography.wrapper.request.DecryptionRequestWrapper;
import com.demo.poc.customer.repository.cryptography.wrapper.request.EncryptionRequestWrapper;
import com.demo.poc.customer.repository.cryptography.wrapper.response.DecryptionResponseWrapper;
import com.demo.poc.customer.repository.cryptography.wrapper.response.EncryptionResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CryptographyRepository {

    private static final String SERVICE_NAME = "cryptography-v1";

    private final RestClientTemplate restTemplate;
    private final ApplicationProperties properties;

    public String encrypt(Map<String, String> headers, String value) {
        RestClient restClient = properties.getRestClients().get(SERVICE_NAME);

        return restTemplate.exchange(ExchangeRequest.<EncryptionRequestWrapper,EncryptionResponseWrapper>builder()
                .url(restClient.getRequest().getEndpoint() + "/encrypt")
                .httpMethod(HttpMethod.POST)
                .requestBody(EncryptionRequestWrapper.builder().value(value).build())
                .responseClass(EncryptionResponseWrapper.class)
                .headers(HeadersFiller.fillHeaders(restClient.getRequest().getHeaders(), headers))
                .build(), SERVICE_NAME)
                .getCipherMessage();
    }

    public String decrypt(Map<String, String> headers, String cipherMessage) {
        RestClient restClient = properties.getRestClients().get(SERVICE_NAME);

        return restTemplate.exchange(ExchangeRequest.<DecryptionRequestWrapper,DecryptionResponseWrapper>builder()
                .url(restClient.getRequest().getEndpoint() + "/decrypt")
                .httpMethod(HttpMethod.POST)
                .requestBody(DecryptionRequestWrapper.builder().cipherMessage(cipherMessage).build())
                .responseClass(DecryptionResponseWrapper.class)
                .headers(HeadersFiller.fillHeaders(restClient.getRequest().getHeaders(), headers))
                .build(), SERVICE_NAME)
                .getValue();
    }
}
