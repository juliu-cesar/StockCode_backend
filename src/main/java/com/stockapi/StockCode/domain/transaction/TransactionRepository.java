package com.stockapi.StockCode.domain.transaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

  @Query("""
      select t.id, t.date, t.client, SUM(p.purchasePrice * p.amount) AS totalPrice, t.description
        from Transaction t
        join ProductTransaction p on p.transactionId = t.id
        group by t.id
        order by t.id
      """)
  // Page<ListTransactionDto> findAllWithPrice(Pageable pagination);
  List<ListTransactionDto> findAllWithPrice();
  
}
