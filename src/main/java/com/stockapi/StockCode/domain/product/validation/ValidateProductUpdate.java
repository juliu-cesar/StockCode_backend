package com.stockapi.StockCode.domain.product.validation;

import com.stockapi.StockCode.domain.product.UpdateProductDto;

public interface ValidateProductUpdate {
  void validate(UpdateProductDto dto);
}
