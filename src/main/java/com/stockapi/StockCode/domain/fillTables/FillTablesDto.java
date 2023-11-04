package com.stockapi.StockCode.domain.fillTables;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotNull;

public record FillTablesDto(
  @NotNull Boolean fill
) {
  
}
