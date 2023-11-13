package com.stockapi.StockCode.domain.product.validation;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.stockapi.StockCode.domain.product.UpdateProductDto;
import com.stockapi.StockCode.infra.exception.ValidationException;

@Component
public class ValidatePriceGreaterThanZero implements ValidateProductUpdate {

  @Override
  public void validate(UpdateProductDto dto) {
    
    if (dto.price() != null) {
      if (dto.price().compareTo(BigDecimal.ZERO) <= 0) {
        throw new ValidationException("Preço deve ser maior que zero.");
      }
    }
  }
  
}
