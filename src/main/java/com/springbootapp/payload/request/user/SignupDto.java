package com.springbootapp.payload.request.user;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotBlank;

public class SignupDto {

  @NotBlank(message = "firstName")
  private String firstName;

  @NotBlank(message = "lastName")
  private String lastName;

  @NotBlank(message = "password")
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "password")
  private String password;

  @NotBlank(message = "phoneNumber")
  private String phoneNumber;

  @NotBlank(message = "requestAuthenticateCode")
  private String requestAuthenticateCode;

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPassword() {
    return password;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getRequestAuthenticateCode() {
    return requestAuthenticateCode;
  }
}
