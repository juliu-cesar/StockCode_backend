package com.stockapi.StockCode.domain.transaction;

import java.time.LocalDateTime;

public record ListTransactionDto(
    Long id,
    LocalDateTime date,
    String client,
    String description) {
  public ListTransactionDto(Transaction transaction) {
    this(transaction.getId(), transaction.getDate(), transaction.getClient(),
        transaction.getDescription());
  }
}
