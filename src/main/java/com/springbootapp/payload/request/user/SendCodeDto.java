package com.springbootapp.payload.request.user;

public class SendCodeDto {
  
  private String phoneNumber;

  private String code;

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getCode() {
    return code;
  }
}
