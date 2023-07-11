package com.stockapi.StockCode.domain.transaction.productReturn;

import jakarta.validation.constraints.NotNull;

public record RefoundListDto(
    @NotNull Long productId,
    Integer amountRefunded,
    ReasonOfReturn reason,
    String description) {
}
