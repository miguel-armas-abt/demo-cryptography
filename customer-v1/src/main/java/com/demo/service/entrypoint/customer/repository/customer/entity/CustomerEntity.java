package com.demo.service.entrypoint.customer.repository.customer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class CustomerEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "unique_code", unique = true)
  private Long uniqueCode;

  @Column(name = "names")
  private String names;

  @Column(name = "last_names")
  private String lastNames;

  @Column(name = "document_type")
  private String documentType;

  @Column(name = "document_number")
  private Integer documentNumber;

  @Column(name = "is_active")
  private boolean active;

  @Column(name = "password")
  private String password;

}
