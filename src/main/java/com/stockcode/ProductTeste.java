package com.stockcode;

import java.math.BigDecimal;

public class ProductTeste {
  private Integer id;
  private String name;
  private BigDecimal price;

  public ProductTeste(Integer id,
      String name,
      BigDecimal price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public Integer getId() {
    return id;
  }

  // public void setId(Integer id) {
  // this.id = id;
  // }
  public String getName() {
    return name;
  }

  // public void setName(String name) {
  // this.name = name;
  // }
  public BigDecimal getPrice() {
    return price;
  }

  // public void setPrice(BigDecimal price) {
  // this.price = price;
  // }
  @Override
  public String toString() {
    return "ID: " + this.id + "\nNome: " + this.name + "\nPreço: " + this.price;
  }
}
