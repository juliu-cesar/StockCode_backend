package com.stockapi.StockCode.domain.brand;

import jakarta.validation.constraints.NotBlank;

public record CreateBrandDto(
    @NotBlank String brandName) {

}
