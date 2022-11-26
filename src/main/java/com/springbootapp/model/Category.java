package com.springbootapp.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "category_name")
  private String categoryName;

  @Column(name = "category_image")
  private String categoryImage;

  // @ManyToMany(mappedBy = "categories")
  // private Set<Shop> shops;

  public Category() {

  }

  public Long getId() {
    return id;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public String getCategoryImage() {
    return categoryImage;
  }

  // public Set<Shop> getShops() {
  //   return shops;
  // }
}
