package com.stockapi.StockCode.domain.product;

import jakarta.validation.constraints.NotNull;

public record ProductListDto(
    @NotNull Long id,
    @NotNull Integer amount) {
}
