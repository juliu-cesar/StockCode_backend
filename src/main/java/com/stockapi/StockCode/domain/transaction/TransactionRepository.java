package com.stockapi.StockCode.domain.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  @Query("""
      select new com.stockapi.StockCode.domain.transaction.ListTransactionDto(
      t.id, t.date, t.client, SUM(b.purchasePrice * b.amount) AS totalPrice, t.description)
        from BuyProduct b
        join b.id.transactionId t
        group by t.id
        order by t.date
      """)
  Page<ListTransactionDto> findAllWithPrice(Pageable pagination);

  @Query("""
      select new com.stockapi.StockCode.domain.transaction.DetailTransactionProductDTO(
      p.id, p.productName, b.brandName, bp.purchasePrice, bp.amount, 
      bp.purchasePrice * bp.amount, bp.returned, bp.description)
        from BuyProduct bp
        join bp.id.productId p
        join p.brand b
        join bp.id.transactionId t
        where t.id = :id
        order by p.productName
      """)
  Page<DetailTransactionProductDTO> findTransactionAndDetailIt(Pageable pagination, Long id);

}

  // @Query("""
  //     select new com.stockapi.StockCode.domain.transaction.DetailTransactionProductDTO(
  //     p.id, p.productName, b.brandName, bp.purchasePrice, bp.amount, bp.purchasePrice * bp.amount, bp.returned, bp.description)
  //       from BuyProduct bp
  //       join bp.id.productId p
  //       join p.brand b
  //       join bp.id.transactionId t
  //       where t.id = :id
  //       order by p.productName
  //     """)