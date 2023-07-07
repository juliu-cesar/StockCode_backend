package com.stockapi.StockCode.domain.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ListTransactionWithPriceDto(
    Long id,
    LocalDateTime date,
    BigDecimal totalPrice,
    String client,
    String description) {
  public ListTransactionWithPriceDto(Transaction transaction, BigDecimal totalPrice) {
    this(transaction.getId(), transaction.getDate(), totalPrice, transaction.getClient(),
        transaction.getDescription());
  }
}