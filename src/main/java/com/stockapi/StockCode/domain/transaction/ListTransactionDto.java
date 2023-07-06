package com.stockapi.StockCode.domain.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ListTransactionDto(
    Long id,
    CategoriesOfMovements movement,
    LocalDateTime date,
    BigDecimal totalPrice,
    String description) {
  public ListTransactionDto(Transaction transaction) {
    this(transaction.getId(), transaction.getMovement(), transaction.getDate(), transaction.getTotalPrice(),
        transaction.getDescription());
  }
}
