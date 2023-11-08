package com.stockapi.StockCode.domain.stock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record AddProductToStockDto(
  @Pattern(regexp = "\\d{5}", message = "Identificador do produto deve conter 5 dígitos.") String productId,
  @NotNull @Positive(message = "Quantidade deve ser maior que 0") Integer amount,
  @NotBlank String location
) {
  
}