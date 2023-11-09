package com.stockapi.StockCode.domain.transaction.RefoundProduct;

import jakarta.validation.constraints.NotNull;

public record ListRefoundProductDto(
    @NotNull Long purchasedItemId,
    @NotNull Integer amountRefunded,
    @NotNull ReasonOfReturn reason,
    String description) {
}
