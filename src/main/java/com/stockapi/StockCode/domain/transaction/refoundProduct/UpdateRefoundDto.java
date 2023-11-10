package com.stockapi.StockCode.domain.transaction.refoundProduct;

public record UpdateRefoundDto(
    Integer amount,
    ReasonOfReturn reason,
    String description) {

}
