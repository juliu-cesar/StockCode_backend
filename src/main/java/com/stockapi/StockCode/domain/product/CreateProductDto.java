package com.stockapi.StockCode.domain.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateProductDto(
    @NotBlank String productName,
    @NotNull @Positive BigDecimal price,
    String description,
    @Pattern(regexp = "\\d{4}", message = "Identificador da marca deve conter 4 dígitos.") String brandId,
    @Pattern(regexp = "\\d{3}", message = "Identificador da categoria deve conter 3 dígitos.") String categoryId) {

}
