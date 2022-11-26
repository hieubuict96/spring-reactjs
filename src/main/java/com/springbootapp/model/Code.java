package com.springbootapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "code")
public class Code {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "email")
  private String email;

  @Column(name = "code")
  private String code;

  @Column(name = "time_send_code")
  private long timeSendCode;

  public Code() {

  }

  public Code(String phoneNumber, String code, long timeSendCode) {
    this.phoneNumber = phoneNumber;
    this.code = code;
    this.timeSendCode = timeSendCode;
  }

  public Code(long timeSendCode, String code, String email) {
    this.timeSendCode = timeSendCode;
    this.code = code;
    this.email = email;
  }

  public long getId() {
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

  public long getTimeSendCode() {
    return timeSendCode;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setTimeSendCode(long timeSendCode) {
    this.timeSendCode = timeSendCode;
  }

}
