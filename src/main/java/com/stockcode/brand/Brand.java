package com.stockcode.brand;

public class Brand {
  private Integer id;
  private String brand_name;

  public Brand(Integer id, String name) {
    this.id = id;
    this.brand_name = name;
  }

  public Integer getId() {
    return id;
  }

  public String getBrand_name() {
    return brand_name;
  }
}
