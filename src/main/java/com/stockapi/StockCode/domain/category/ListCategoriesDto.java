package com.stockapi.StockCode.domain.category;

public record ListCategoriesDto(
    Long id,
    String name) {

  public ListCategoriesDto(Category category) {
    this(category.getId(), category.getCategoryName());
  }
}
