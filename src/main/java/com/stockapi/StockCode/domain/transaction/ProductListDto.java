package com.stockapi.StockCode.domain.transaction;

import jakarta.validation.constraints.NotNull;

public record ProductListDto(
    @NotNull Long id,
    @NotNull Integer amount,
    String description) {
}
