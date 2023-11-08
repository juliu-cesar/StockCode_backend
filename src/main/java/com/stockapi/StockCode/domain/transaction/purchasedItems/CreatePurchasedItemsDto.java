package com.stockapi.StockCode.domain.transaction.purchasedItems;

import java.math.BigDecimal;

import com.stockapi.StockCode.domain.transaction.Transaction;

public record CreatePurchasedItemsDto(
    Transaction transaction,
    Long productId,
    String productName,
    String productBrand,
    String productCategory,
    BigDecimal purchasePrice,
    Integer amount,
    Boolean returned,
    String description) {

}
