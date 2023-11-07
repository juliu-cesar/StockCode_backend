package com.stockapi.StockCode.domain.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateProductDto(
    @Pattern(regexp = "\\d{9}", message = "Identificador do produto deve conter 9 dígitos.") String id,
    @NotBlank String productName,
    @NotNull @Positive BigDecimal price,
    String description,
    @Pattern(regexp = "\\d{8}", message = "Identificador do produto deve conter 8 dígitos.") String brandId,
    @Pattern(regexp = "\\d{8}", message = "Identificador do produto deve conter 8 dígitos.") String categoryId) {

}
