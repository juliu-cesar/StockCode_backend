package com.stockapi.StockCode.domain.transaction;

import java.math.BigDecimal;

import jakarta.persistence.Column;

public record DetailTransactionProductDTO(
    @Column(name = "id") Long id,
    @Column(name = "productId") Long productId,
    @Column(name = "productName") String productName,
    @Column(name = "productBrand") String brand,
    @Column(name = "productCategory") String category,
    @Column(name = "purchasePrice") BigDecimal price,
    @Column(name = "amount") Integer amount,
    @Column(name = "totalPrice") BigDecimal totalPrice,
    @Column(name = "returned") Boolean returned,
    @Column(name = "description") String description) {
}