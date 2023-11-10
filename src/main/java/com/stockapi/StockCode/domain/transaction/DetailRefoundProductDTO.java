package com.stockapi.StockCode.domain.transaction;

import java.math.BigDecimal;

import com.stockapi.StockCode.domain.transaction.refoundProduct.ReasonOfReturn;

import jakarta.persistence.Column;

public record DetailRefoundProductDTO(
    @Column(name = "id") Long purchasedItemId,
    @Column(name = "productId") Long productId,
    @Column(name = "productName") String product,
    @Column(name = "productBrand") String Brand,
    @Column(name = "priceRefunded") BigDecimal priceRefunded,
    @Column(name = "amountRefunded") Integer amountRefunded,
    @Column(name = "totalPriceRefunded") BigDecimal totalPriceRefunded,
    @Column(name = "reasonReturn") ReasonOfReturn reasonReturn,
    @Column(name = "description") String description) {
}