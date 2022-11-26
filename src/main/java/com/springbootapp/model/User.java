package com.springbootapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

import javax.persistence.CascadeType;

@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "first_name", length = 255)
  private String firstName;

  @Column(name = "last_name", length = 255)
  @NotBlank
  private String lastName;

  @Column(name = "email", length = 255, unique = true)
  private String email;

  @Column(name = "hash_password", length = 255)
  private String hashPassword;

  @Column(name = "phone_number", length = 255, unique = true)
  private String phoneNumber;

  @Column(name = "address", length = 255)
  private String address;

  @Column(name = "img_user", length = 255)
  private String imgUser;

  @Column(name = "user_id_facebook", unique = true)
  private String userIdFacebook;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "shop") // đây là tên cột của bảng user mà dùng để tham chiếu đến primary key của bảng shop
  private Shop shop; // nhờ đoạn code này mà chương trình biết được cột shop của bảng user cần được tham chiếu đến primary key của bảng shop

  public User() {
  }

  public User(String firstName, String lastName, String
  hashPassword, String phoneNumber) {
  this.firstName = firstName;
  this.lastName = lastName;
  this.hashPassword = hashPassword;
  this.phoneNumber = phoneNumber;
  }

  public User(String firstName, String lastName, String email, String
  hashPassword, String phoneNumber, String address, String imgUser, String
  userIdFacebook, Shop shop) {
  this.firstName = firstName;
  this.lastName = lastName;
  this.email = email;
  this.hashPassword = hashPassword;
  this.phoneNumber = phoneNumber;
  this.address = address;
  this.imgUser = imgUser;
  this.userIdFacebook = userIdFacebook;
  this.shop = shop;
  }

  public Long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getHashPassword() {
    return hashPassword;
  }

  public void setHashPassword(String hashPassword) {
    this.hashPassword = hashPassword;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getImgUser() {
    return imgUser;
  }

  public void setImgUser(String imgUser) {
    this.imgUser = imgUser;
  }

  public String getUserIdFacebook() {
    return userIdFacebook;
  }

  public void setUserIdFacebook(String userIdFacebook) {
    this.userIdFacebook = userIdFacebook;
  }

  public Shop getShop() {
    return shop;
  }

  public void setShop(Shop shop) {
    this.shop = shop;
  }
}
