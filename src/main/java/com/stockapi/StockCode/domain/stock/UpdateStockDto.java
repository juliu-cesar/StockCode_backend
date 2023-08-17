package com.stockapi.StockCode.domain.stock;

import jakarta.validation.constraints.NotNull;

public record UpdateStockDto(
    @NotNull Long id,
    Integer amount,
    String location) {
}
