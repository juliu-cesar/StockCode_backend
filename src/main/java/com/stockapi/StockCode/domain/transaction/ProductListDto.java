package com.stockapi.StockCode.domain.transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record ProductListDto(
    @NotNull @Pattern(regexp = "\\d{9}", message = "Identificador do produto deve conter 9 dígitos.") String id,
    @NotNull @Positive(message = "Quantidade deve ser maior que 0.") Integer amount,
    String description) {
}
