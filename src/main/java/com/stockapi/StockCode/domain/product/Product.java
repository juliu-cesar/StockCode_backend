package com.stockapi.StockCode.domain.product;

import java.math.BigDecimal;

import com.stockapi.StockCode.domain.brand.Brand;
import com.stockapi.StockCode.domain.category.Category;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "product")
@Entity(name = "Product")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {

  @Id
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "brand_id")
  private Brand brand;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;
  
  private String productName;

  private BigDecimal price;

  private String description;

  @Override
  public String toString() {
    return "Product [id=" + id + ", productName=" + productName + ", price=" + price + ", description=" + description
        + ", brand=" + brand + ", category=" + category + "]";
  }

  
}
