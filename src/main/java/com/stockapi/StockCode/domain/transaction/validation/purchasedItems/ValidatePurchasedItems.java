package com.stockapi.StockCode.domain.transaction.validation.purchasedItems;

import com.stockapi.StockCode.domain.transaction.CreateTransactionDto;

public interface ValidatePurchasedItems {
  void validate(CreateTransactionDto dto);
}
