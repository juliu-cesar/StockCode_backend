package com.stockapi.StockCode.domain.transaction.refoundProduct;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record RefoundDto(
    @NotNull Long transactionId,    
    @NotNull @Valid List<ListRefoundProductDto> listRefoundProduct) {
}
