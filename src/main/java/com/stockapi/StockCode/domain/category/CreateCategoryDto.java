package com.stockapi.StockCode.domain.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateCategoryDto(
    @Pattern(regexp = "\\d{8}", message = "Identificador da categoria deve conter 8 dígitos.") String id,
    @NotBlank String categoryName) {
}
