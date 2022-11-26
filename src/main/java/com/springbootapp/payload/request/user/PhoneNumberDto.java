package com.springbootapp.payload.request.user;

import javax.validation.constraints.Pattern;

public class PhoneNumberDto {

  @Pattern(regexp = "^\\+[0-9]+$")
  private String phoneNumber;

  public String getPhoneNumber() {
    return phoneNumber;
  }
}
