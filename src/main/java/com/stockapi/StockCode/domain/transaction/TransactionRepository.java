package com.stockapi.StockCode.domain.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  @Query("""
      select new com.stockapi.StockCode.domain.transaction.ListTransactionDto(
      t.id, t.date, t.client, SUM(p.purchasePrice * p.amount) AS totalPrice, t.description)
        from ProductTransaction p
        join p.id.transactionId t
        group by t.id
        order by t.date
      """)
  Page<ListTransactionDto> findAllWithPrice(Pageable pagination);

  @Query("""
      select new com.stockapi.StockCode.domain.transaction.DetailTransactionDTO(
      p.id, p.productName, b.brandName, pt.purchasePrice, pt.amount, (pt.purchasePrice * pt.amount) AS totalPrice, pt.categoriesMovements)
        from ProductTransaction pt
        join pt.id.productId p
        join p.brand b
        join pt.id.transactionId t
        where t.id = :id
        order by p.productName
      """)
  Page<DetailTransactionDTO> findTransactionAndDetailIt(Pageable pagination, Long id);

}