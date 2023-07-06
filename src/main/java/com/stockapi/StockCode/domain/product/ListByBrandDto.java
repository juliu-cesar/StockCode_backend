package com.stockapi.StockCode.domain.product;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotNull;

public record ListByBrandDto(
    @NotNull @JsonAlias({"marca"}) Long brandId) {
}
