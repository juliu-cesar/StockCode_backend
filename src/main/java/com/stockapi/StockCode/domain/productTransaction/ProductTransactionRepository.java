package com.stockapi.StockCode.domain.productTransaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTransactionRepository extends JpaRepository<ProductTransaction, Long> {
  // @Query("""
  //       select pt from ProductTransaction pt
  //         join pt.id.productId pId
  //         join pt.id.transactionId tId
  //         where pId.id = :productId          
  //         and tId.id = :transactionId
  //     """)
  // ProductTransaction findById(Long productId, Long transactionId);
  ProductTransaction findById(ProductTransactionId productTransactionId);
}
