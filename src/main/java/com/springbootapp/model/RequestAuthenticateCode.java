package com.springbootapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "request_authenticate_code")
public class RequestAuthenticateCode {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "phone_number", length = 32, unique = true)
  private String phoneNumber;

  @Column(name = "email", length = 255, unique = true)
  private String email;

  @Column(name = "code", length = 32, nullable = false)
  private String code;

  public RequestAuthenticateCode() {

  }

  public RequestAuthenticateCode(String phoneNumber, String email, String code) {
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.code = code;
  }

  public Long getId() {
    return id;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

}
