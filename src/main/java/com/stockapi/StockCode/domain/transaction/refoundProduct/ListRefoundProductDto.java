package com.stockapi.StockCode.domain.transaction.refoundProduct;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ListRefoundProductDto(
    @NotNull Long purchasedItemId,
    @Positive(message = "Quantidade deve ser maior que 0.") Integer amountRefunded,
    @NotNull ReasonOfReturn reason,
    @NotNull Boolean returnedToStock,
    String description) {
}
