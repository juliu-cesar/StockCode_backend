package com.stockapi.StockCode.domain.transaction;

import java.math.BigDecimal;

import com.stockapi.StockCode.domain.transaction.productReturn.ReasonOfReturn;

import jakarta.persistence.Column;

public record DetailProductReturnDTO(
    @Column(name = "id") Long id,
    @Column(name = "productName") String productName,
    @Column(name = "brandName") String brandName,
    @Column(name = "priceRefunded") BigDecimal priceRefunded,
    @Column(name = "amountRefunded") Integer amountRefunded,
    @Column(name = "totalPriceRefunded") BigDecimal totalPriceRefunded,
    @Column(name = "reasonReturn") ReasonOfReturn reasonReturn,
    @Column(name = "description") String description) {
}