package com.stockapi.StockCode.domain.stock;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockRepository extends JpaRepository<Stock, Long> {

  @Query("""
        select s from Stock s
          join s.product p
          where p.id = :id
      """)
  Optional<Stock> findByProductId(Long id);
}
