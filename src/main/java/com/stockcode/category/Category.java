package com.stockcode.category;

public class Category {
  private Integer id;
  private String category;

  public Category(Integer id, String category) {
    this.id = id;
    this.category = category;
  }

  public Integer getId() {
    return id;
  }

  public String getCategory() {
    return category;
  }
}
