package com.stockapi.StockCode.domain.transaction;

import java.util.List;

import com.stockapi.StockCode.domain.product.ProductReturnListDto;

import jakarta.validation.constraints.NotNull;

public record ProductReturnDto(
    @NotNull Long transactionId,
    @NotNull List<ProductReturnListDto> productReturnList) {
}
