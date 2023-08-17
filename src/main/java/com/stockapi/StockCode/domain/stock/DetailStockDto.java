package com.stockapi.StockCode.domain.stock;

import com.stockapi.StockCode.domain.product.ListProductsDto;

public record DetailStockDto(
    Long id,
    ListProductsDto product,
    Integer amount,
    String location) {
  public DetailStockDto(Stock entity) {
    this(entity.getId(), new ListProductsDto(entity.getProduct()), entity.getAmount(), entity.getLocation());
  }
}
