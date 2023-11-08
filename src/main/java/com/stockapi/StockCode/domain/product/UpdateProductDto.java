package com.stockapi.StockCode.domain.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.Pattern;

public record UpdateProductDto(
    @Pattern(regexp = "\\d{5}", message = "Identificador do produto deve conter 5 dígitos.") String id,
    String productName,
    BigDecimal price,
    String description,
    Long brandId,
    Long categoryId) {

}
