package com.demo.poc.entrypoint.customer.repository.cryptography;

import com.demo.poc.commons.core.errors.dto.ErrorDto;
import com.demo.poc.commons.core.properties.restclient.RestClient;
import com.demo.poc.commons.core.restclient.RestClientTemplate;
import com.demo.poc.commons.properties.ApplicationProperties;
import com.demo.poc.entrypoint.customer.repository.cryptography.wrapper.request.DecryptionRequestWrapper;
import com.demo.poc.entrypoint.customer.repository.cryptography.wrapper.request.EncryptionRequestWrapper;
import com.demo.poc.entrypoint.customer.repository.cryptography.wrapper.response.DecryptionResponseWrapper;
import com.demo.poc.entrypoint.customer.repository.cryptography.wrapper.response.EncryptionResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CryptographyRepository {

    private static final String SERVICE_NAME = "cryptography-v1";

    private final RestClientTemplate restTemplate;
    private final ApplicationProperties properties;

    public String encrypt(Map<String, String> headers, String value) {
        RestClient restClient = properties.searchRestClient(SERVICE_NAME);

        return restTemplate
            .service(SERVICE_NAME)
            .post()
            .uri(restClient.getRequest().getEndpoint() + "/encrypt")
            .headers(headers)
            .body(EncryptionRequestWrapper.builder().value(value).build())
            .retrieve(EncryptionResponseWrapper.class, ErrorDto.class)
            .getCipherMessage();
    }

    public String decrypt(Map<String, String> headers, String cipherMessage) {
        RestClient restClient = properties.searchRestClient(SERVICE_NAME);

        return restTemplate
            .service(SERVICE_NAME)
            .post()
            .uri(restClient.getRequest().getEndpoint() + "/decrypt")
            .headers(headers)
            .body(DecryptionRequestWrapper.builder().cipherMessage(cipherMessage).build())
            .retrieve(DecryptionResponseWrapper.class, ErrorDto.class)
            .getValue();
    }
}
