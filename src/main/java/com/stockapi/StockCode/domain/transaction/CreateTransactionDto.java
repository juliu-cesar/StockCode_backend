package com.stockapi.StockCode.domain.transaction;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stockapi.StockCode.domain.productTransaction.ProductListDto;

import jakarta.validation.constraints.NotNull;

public record CreateTransactionDto(
    @NotNull @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime date,
    String client,
    String description,
    @NotNull List<ProductListDto> productList) {
}