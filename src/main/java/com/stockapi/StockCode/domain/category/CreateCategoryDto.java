package com.stockapi.StockCode.domain.category;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryDto(
    @NotBlank String categoryName) {
}
