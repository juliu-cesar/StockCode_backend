package com.stockapi.StockCode.domain.transaction.productReturn;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record RefoundDto(
    @NotNull Long transactionId,
    @NotNull List<RefoundListDto> productReturnList) {
}
