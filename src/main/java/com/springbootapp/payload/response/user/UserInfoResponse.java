package com.springbootapp.payload.response.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.springbootapp.model.Category;
import com.springbootapp.model.User;
import com.springbootapp.security.jwt.TokenProvider;

public class UserInfoResponse {
  private Map<String, Object> user = new HashMap<>();

  private String accessToken;

  public UserInfoResponse(User userData, TokenProvider tokenProvider) {
    user.put("_id", userData.getId());

    if (userData.getFirstName() != null) {
      user.put("firstName", userData.getFirstName());
    }

    user.put("lastName", userData.getLastName());

    if (userData.getEmail() != null) {
      user.put("email", userData.getEmail());
    }

    if (userData.getPhoneNumber() != null) {
      user.put("phoneNumber", userData.getPhoneNumber());
    }

    if (userData.getAddress() != null) {
      user.put("address", userData.getAddress());
    }

    if (userData.getImgUser() != null) {
      user.put("imgBuyer", userData.getImgUser());
    }

    Map<String, Object> shopResponse = new HashMap<>();

    if (userData.getShop() != null) {
      shopResponse.put("shopName", userData.getShop().getShopName());
      shopResponse.put("imgShop", userData.getShop().getImgShop());

      List<Long> categories = new ArrayList<>();

      for (Category category : userData.getShop().getCategories()) {
        categories.add(category.getId());
      }

      shopResponse.put("categories", categories);
    } else {
      shopResponse.put("categories", new ArrayList<>());
    }

    user.put("shop", shopResponse);

    accessToken = tokenProvider.generateToken(userData.getId());
  }

  public Map<String, Object> getUser() {
    return user;
  }

  public String getAccessToken() {
    return accessToken;
  }
}
