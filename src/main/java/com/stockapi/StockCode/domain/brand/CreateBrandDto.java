package com.stockapi.StockCode.domain.brand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateBrandDto(
    @Pattern(regexp = "\\d{8}", message = "Identificador da marca deve conter 8 dígitos.") String id,
    @NotBlank String brandName) {

}
