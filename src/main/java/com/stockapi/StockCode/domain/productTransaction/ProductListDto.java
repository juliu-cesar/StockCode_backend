package com.stockapi.StockCode.domain.productTransaction;

import jakarta.validation.constraints.NotNull;

public record ProductListDto(
    @NotNull Long id,
    @NotNull Integer amount) {
}
