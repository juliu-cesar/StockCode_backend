package com.stockapi.StockCode.domain.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;

public record ListTransactionDto(
    @Column(name = "id") Long id,
    @Column(name = "date") LocalDateTime date,
    @Column(name = "client") String client,
    @Column(name = "totalPrice") BigDecimal totalPrice,
    @Column(name = "description") String description) {

}
