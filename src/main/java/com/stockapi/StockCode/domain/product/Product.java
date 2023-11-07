package com.stockapi.StockCode.domain.product;

import java.math.BigDecimal;
import java.util.Optional;

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

  public Product(CreateProductDto dto, Brand b, Category c) {
    this.id = Long.valueOf(dto.id());
    this.brand = b;
    this.category = c;
    this.productName = dto.productName();
    this.price = dto.price();
    this.description = dto.description();
  }

  public void update(UpdateProductDto dto, Optional<Brand> brand, Optional<Category> category){
    if (brand.isPresent()) {
      this.brand = brand.get();
    }
    if (category.isPresent()) {
      this.category = category.get();
    }
    if (dto.productName() != null) {
      this.productName = dto.productName();
    }
    if (dto.price() != null) {
        this.price = dto.price();      
    }
    if(dto.description() != null){
      this.description = dto.description();
    }
  }

  @Override
  public String toString() {
    return "id=" + id + ", product-name=" + productName + ", price=" + price + ", description=" + description
        + ", brand=" + brand + ", category=" + category;
  }

}
