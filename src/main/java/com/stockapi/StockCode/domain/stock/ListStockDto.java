package com.stockapi.StockCode.domain.stock;

public record ListStockDto(
    Long id,
    Long productId,
    Integer amount,
    String location) {
  public ListStockDto(Stock entity) {
    this(entity.getId(), entity.getProduct().getId(), entity.getAmount(), entity.getLocation());
  }
}
