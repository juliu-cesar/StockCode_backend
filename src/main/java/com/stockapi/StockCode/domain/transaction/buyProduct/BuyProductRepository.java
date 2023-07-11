package com.stockapi.StockCode.domain.transaction.buyProduct;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyProductRepository extends JpaRepository<BuyProduct, Long> {

  BuyProduct findById(BuyProductId productTransactionId);
}
