package com.stockapi.StockCode.domain.transaction;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record ProductListDto(
    @Pattern(regexp = "\\d{5}", message = "Identificador do produto deve conter 5 dígitos.") String id,
    @Positive(message = "Quantidade deve ser maior que 0.") Integer amount,
    String description) {
}
