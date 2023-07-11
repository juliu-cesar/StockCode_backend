package com.stockapi.StockCode.domain.product.productReturn;

import jakarta.validation.constraints.NotNull;

public record ProductReturnListDto(
    @NotNull Long productId,
    ReasonOfReturn reason,
    String description) {
}
