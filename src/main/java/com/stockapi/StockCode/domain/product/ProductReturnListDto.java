package com.stockapi.StockCode.domain.product;

import jakarta.validation.constraints.NotNull;

public record ProductReturnListDto(
    @NotNull Long productId,
    ReasonOfReturn reason,
    String description) {
}
