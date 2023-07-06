package com.stockapi.StockCode.domain.product;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotNull;

public record ListByRangePriceDto(
    @NotNull @JsonAlias({"menorPreco"}) BigDecimal lowestPrice,
    @NotNull @JsonAlias({"maiorPreco"}) BigDecimal biggestPrice) {
}
