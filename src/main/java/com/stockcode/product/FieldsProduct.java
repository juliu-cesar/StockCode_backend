package com.stockcode.product;

import java.math.BigDecimal;

public record FieldsProduct(Integer id,
    Integer brand_id,
    Integer category_id,
    String name,
    BigDecimal price,
    String product_description) {
}
