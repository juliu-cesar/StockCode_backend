package com.stockapi.StockCode.domain.product;

import java.math.BigDecimal;

public record ListProductsDto(
    Long id,
    String productName,
    BigDecimal price,
    String description,
    String brand,
    String category) {
  public ListProductsDto(Product product) {
    this(product.getId(), product.getProductName(), product.getPrice(), product.getDescription(),
        product.getBrand().getBrandName(),
        product.getCategory().getCategoryName());
  }
}
