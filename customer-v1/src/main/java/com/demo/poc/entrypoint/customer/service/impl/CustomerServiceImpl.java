package com.demo.poc.entrypoint.customer.service.impl;

import com.demo.poc.entrypoint.customer.exceptions.CustomerAlreadyExistsException;
import com.demo.poc.entrypoint.customer.exceptions.CustomerNotFoundException;
import com.demo.poc.entrypoint.customer.repository.cryptography.CryptographyRepository;
import com.demo.poc.entrypoint.customer.service.CustomerService;
import com.demo.poc.entrypoint.customer.dto.request.CustomerRequestDto;
import com.demo.poc.entrypoint.customer.dto.response.CustomerResponseDto;
import com.demo.poc.entrypoint.customer.mapper.CustomerMapper;
import com.demo.poc.entrypoint.customer.repository.customer.CustomerRepository;
import com.demo.poc.entrypoint.customer.enums.DocumentType;
import com.demo.poc.entrypoint.customer.repository.customer.entity.CustomerEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;
  private final CryptographyRepository cryptographyRepository;

  @Override
  public List<CustomerResponseDto> findByDocumentType(Map<String, String> headers, String documentType) {
    return (Optional.ofNullable(documentType).isEmpty()
          ? customerRepository.findAll()
          : this.validateCustomerAndFindByDocumentType(headers, documentType))
        .stream()
        .map(customerMapper::toResponseDto)
        .collect(Collectors.toList());
  }

  private List<CustomerEntity> validateCustomerAndFindByDocumentType(Map<String, String> headers, String documentType) {
    DocumentType.validateDocumentType.accept(documentType);
    return customerRepository.findByDocumentType(documentType);
  }

  @Override
  public CustomerResponseDto findByUniqueCode(Map<String, String> headers, Long uniqueCode) {
    return customerRepository.findByUniqueCode(uniqueCode)
        .map(customerMapper::toResponseDto)
        .orElseThrow(CustomerNotFoundException::new);
  }

  @Override
  public Long save(Map<String, String> headers, CustomerRequestDto customerRequest) {
    if (customerRepository.findByUniqueCode(customerRequest.getUniqueCode()).isPresent()) {
      throw new CustomerAlreadyExistsException();
    }
    String cipheredPassword = cryptographyRepository.encrypt(headers, customerRequest.getPassword());
    return customerRepository.save(customerMapper.toEntity(customerRequest, cipheredPassword)).getUniqueCode();
  }

  @Override
  public Long update(Map<String, String> headers, Long uniqueCode, CustomerRequestDto customerRequest) {
    return customerRepository.findByUniqueCode(uniqueCode)
      .map(customerFound -> {
        String cipheredPassword = cryptographyRepository.encrypt(headers, customerRequest.getPassword());

        CustomerEntity customerEntity = customerMapper.toEntity(customerRequest, cipheredPassword);
        customerEntity.setId(customerFound.getId());
        customerEntity.setUniqueCode(customerFound.getUniqueCode());
        customerRepository.save(customerEntity);
        return customerEntity.getUniqueCode();
      })
      .orElseThrow(CustomerNotFoundException::new);
  }

  @Override
  public Long deleteByUniqueCode(Map<String, String> headers, Long uniqueCode) {
    return customerRepository.findByUniqueCode(uniqueCode)
        .map(menuOptionFound -> {
          customerRepository.deleteById(menuOptionFound.getId());
          return menuOptionFound.getUniqueCode();
        })
        .orElseThrow(CustomerNotFoundException::new);
  }

}
