package com.demo.service.entrypoint.customer.service;

import java.util.List;
import java.util.Map;

import com.demo.service.entrypoint.customer.dto.request.CustomerRequestDto;
import com.demo.service.entrypoint.customer.dto.response.CustomerResponseDto;

public interface CustomerService {

  List<CustomerResponseDto> findByDocumentType(Map<String, String> headers, String documentType);

  CustomerResponseDto findByUniqueCode(Map<String, String> headers, Long uniqueCode);

  Long save (Map<String, String> headers, CustomerRequestDto customerRequest);

  Long update(Map<String, String> headers, Long uniqueCode, CustomerRequestDto customerRequest);

  Long deleteByUniqueCode(Map<String, String> headers, Long uniqueCode);
}
