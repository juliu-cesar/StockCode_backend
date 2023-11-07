package com.stockapi.StockCode.domain.transaction.validation;

import com.stockapi.StockCode.domain.transaction.CreateTransactionDto;

public interface ValidatePurchasedItems {
  void validate(CreateTransactionDto dto);
}
