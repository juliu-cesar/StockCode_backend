package com.stockapi.StockCode.domain.transaction.productReturn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.stockapi.StockCode.domain.transaction.DetailProductReturnDTO;

public interface ProductReturnRepository extends JpaRepository<ProductReturn, Long> {

  @Query("""
      select new com.stockapi.StockCode.domain.transaction.DetailProductReturnDTO(
        p.id, p.productName, b.brandName, pr.priceRefunded, pr.amountRefunded, pr.priceRefunded * pr.amountRefunded AS totalPriceRefunded, 
        pr.reasonReturn, pr.description)
        from ProductReturn pr
          join pr.id.productId p
          join p.brand b
          join pr.id.transactionId t
          where t.id = :id
          order by p.productName
          """)
  Page<DetailProductReturnDTO> findProductReturnAndDetailIt(Pageable pagination, Long id);

}
