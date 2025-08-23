package com.demo.poc.entrypoint.customer.rest;

import com.demo.poc.commons.core.restserver.utils.RestServerUtils;
import com.demo.poc.commons.core.validations.headers.DefaultHeaders;
import com.demo.poc.commons.core.validations.ParamValidator;
import com.demo.poc.entrypoint.customer.dto.request.CustomerRequestDto;
import com.demo.poc.entrypoint.customer.dto.response.CustomerResponseDto;
import com.demo.poc.entrypoint.customer.params.CustomerParam;
import com.demo.poc.entrypoint.customer.service.CustomerService;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.function.LongFunction;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/poc/customer/v1/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerRestService {

  private final CustomerService service;
  private final ParamValidator paramValidator;

  @GetMapping(value = "/{uniqueCode}")
  public ResponseEntity<CustomerResponseDto> findByUniqueCode(HttpServletRequest servletRequest,
                                                              @PathVariable(name = "uniqueCode") Long uniqueCode) {
    Map<String, String> headers = RestServerUtils.extractHeadersAsMap(servletRequest);
    paramValidator.validate(headers, DefaultHeaders.class);

    return ResponseEntity.ok(service.findByUniqueCode(headers, uniqueCode));
  }

  @GetMapping
  public ResponseEntity<List<CustomerResponseDto>> findByDocumentType(HttpServletRequest servletRequest) {
    Map<String, String> headers = RestServerUtils.extractHeadersAsMap(servletRequest);
    paramValidator.validate(headers, DefaultHeaders.class);
    CustomerParam customerParam = paramValidator.validateAndGet(RestServerUtils.extractQueryParamsAsMap(servletRequest), CustomerParam.class);

    List<CustomerResponseDto> customerResponseDtoList = service.findByDocumentType(headers, customerParam.getDocumentType());
    return (customerResponseDtoList == null || customerResponseDtoList.isEmpty())
      ? ResponseEntity.noContent().build()
      : ResponseEntity.ok(service.findByDocumentType(headers, customerParam.getDocumentType()));
  }

  @PostMapping
  public ResponseEntity<Void> save(HttpServletRequest servletRequest,
                                   @Valid @RequestBody CustomerRequestDto customerRequestDto) {
    Map<String, String> headers = RestServerUtils.extractHeadersAsMap(servletRequest);
    paramValidator.validate(headers, DefaultHeaders.class);

    Long uniqueCode = service.save(headers, customerRequestDto);
    return ResponseEntity
      .created(buildPostUriLocation.apply(uniqueCode))
      .build();
  }

  @PutMapping(value = "/{uniqueCode}")
  public ResponseEntity<Void> update(HttpServletRequest servletRequest,
                                     @Valid @RequestBody CustomerRequestDto customerRequestDto,
                                     @PathVariable("uniqueCode") Long uniqueCode) {
    Map<String, String> headers = RestServerUtils.extractHeadersAsMap(servletRequest);
    paramValidator.validate(headers, DefaultHeaders.class);

    uniqueCode = service.update(headers, uniqueCode, customerRequestDto);
    return ResponseEntity
      .created(buildUriLocation.apply(uniqueCode))
      .build();
  }

  @DeleteMapping(value = "/{uniqueCode}")
  public ResponseEntity<Void> delete(HttpServletRequest servletRequest,
                                     @PathVariable("uniqueCode") Long uniqueCode) {
    Map<String, String> headers = RestServerUtils.extractHeadersAsMap(servletRequest);
    paramValidator.validate(headers, DefaultHeaders.class);

    uniqueCode = service.deleteByUniqueCode(headers, uniqueCode);
    return ResponseEntity
      .noContent()
      .location(buildUriLocation.apply(uniqueCode))
      .build();
  }

  private static final LongFunction<URI> buildPostUriLocation = uniqueCode ->
    ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{uniqueCode}")
      .buildAndExpand(uniqueCode)
      .toUri();

  private static final LongFunction<URI> buildUriLocation = productCode ->
    ServletUriComponentsBuilder.fromCurrentRequest()
      .buildAndExpand()
      .toUri();
}
