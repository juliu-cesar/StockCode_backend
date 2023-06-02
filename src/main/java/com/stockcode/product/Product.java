package com.stockcode.product;

import java.math.BigDecimal;

public class Product {
    private Integer id;
    private Integer brand_id;
    private Integer category_id;
    private String name;
    private BigDecimal price;
    private String product_description;
  
    public Product(Integer id,
        Integer brand_id,
        Integer category_id,
        String name,
        BigDecimal price,
        String product_description) {
      this.id = id;
      this.brand_id = brand_id;
      this.category_id = category_id;
      this.name = name;
      this.price = price;
      this.product_description = product_description;
    }
  
    public Integer getId() {
      return id;
    }
  
    public Integer getBrand_id() {
      return brand_id;
    }
  
    public Integer getCategory_id() {
      return category_id;
    }
  
    public String getName() {
      return name;
    }
  
    public BigDecimal getPrice() {
      return price;
    }
  
    public String getProduct_description() {
      return product_description;
    }
  }
  