package com.stockapi.StockCode.domain.transaction.purchasedItems;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasedItemsRepository extends JpaRepository<PurchasedItems, Long> {

  PurchasedItems findById(PurchasedItemsId productTransactionId);
}
