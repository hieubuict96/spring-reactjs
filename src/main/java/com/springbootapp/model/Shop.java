package com.springbootapp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.GenerationType;

@Entity
@Table(name = "shop")
public class Shop {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "img_shop", length = 255)
  private String imgShop;

  @Column(name = "shop_name", length = 255)
  private String shopName;

  // @OneToOne(mappedBy = "shop") //shop là tên của trường bên user tương ứng với cột trong bảng để tham chiếu tới id của bảng này. Cần phải có mappedBy này để phân biệt được cái nào tham chiếu đến vì có thể nhiều cột cùng tham chiếu đến bảng này
  // private User user;

  @ManyToMany()
  @JoinTable(name = "shop_category", joinColumns = @JoinColumn(name = "shop_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
  private Set<Category> categories = new HashSet<>();

  public Long getId() {
    return id;
  }

  public String getImgShop() {
    return imgShop;
  }

  public void setImgShop(String imgShop) {
    this.imgShop = imgShop;
  }

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  public Set<Category> getCategories() {
    return categories;
  }
}
