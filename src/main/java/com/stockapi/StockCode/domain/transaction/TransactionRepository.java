package com.stockapi.StockCode.domain.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  @Query("""
      select new com.stockapi.StockCode.domain.transaction.ListTransactionDto(
      t.id, t.date, t.client, SUM(pi.purchasePrice * pi.amount) AS totalPrice, t.description)
        from PurchasedItems pi
        join pi.id.transactionId t
        group by t.id
        order by t.date
      """)
  Page<ListTransactionDto> findAllWithPrice(Pageable pagination);

  @Query("""
      select new com.stockapi.StockCode.domain.transaction.DetailTransactionProductDTO(
      p.id, p.productName, b.brandName, pi.purchasePrice, pi.amount, 
      pi.purchasePrice * pi.amount, pi.returned, pi.description)
        from PurchasedItems pi
        join pi.id.productId p
        join p.brand b
        join pi.id.transactionId t
        where t.id = :id
        order by p.productName
      """)
  Page<DetailTransactionProductDTO> findTransactionAndDetailIt(Pageable pagination, Long id);

}