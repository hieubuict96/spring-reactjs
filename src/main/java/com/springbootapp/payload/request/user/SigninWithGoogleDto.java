package com.springbootapp.payload.request.user;

public class SigninWithGoogleDto {
  private Data data;

  public Data getData() {
    return data;
  }

  public class Data {
    private String email;

    private String familyName;

    private String givenName;

    private String imageUrl;

    public String getEmail() {
      return email;
    }

    public String getFamilyName() {
      return familyName;
    }

    public String getGivenName() {
      return givenName;
    }

    public String getImageUrl() {
      return imageUrl;
    }
  }
}
