package com.stockapi.StockCode.domain.productTransaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTransactionRepository extends JpaRepository<ProductTransaction, Long>{
  
}
