package com.stockapi.StockCode.domain.brand;

public record ListBrandsDto(
    Long id,
    String name) {

  public ListBrandsDto(Brand brand) {
    this(brand.getId(), brand.getBrandName());
  }
}
