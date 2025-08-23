package com.demo.poc.entrypoint.customer.mapper;

import com.demo.poc.entrypoint.customer.dto.request.CustomerRequestDto;
import com.demo.poc.entrypoint.customer.dto.response.CustomerResponseDto;
import com.demo.poc.entrypoint.customer.repository.customer.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

  CustomerResponseDto toResponseDto(CustomerEntity customerEntity);

  @Mapping(target = "password", source = "cipheredPassword")
  CustomerEntity toEntity(CustomerRequestDto menuOption, String cipheredPassword);
}
