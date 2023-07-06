package com.stockapi.StockCode.domain.category;

public record ListCategoriesDto(
    String name) {

  public ListCategoriesDto(Category category) {
    this(category.getCategoryName());
  }
}
