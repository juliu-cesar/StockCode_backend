package com.stockapi.StockCode.domain.transaction.refoundProduct;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.stockapi.StockCode.domain.transaction.DetailRefoundProductDTO;

public interface RefoundProductRepository extends JpaRepository<RefoundProduct, Long> {

  @Query("""
      select new com.stockapi.StockCode.domain.transaction.DetailRefoundProductDTO(
        pi.id, pi.productId, pi.productName, pi.productBrand, rp.priceRefunded, rp.amountRefunded, rp.priceRefunded * rp.amountRefunded AS totalPriceRefunded,
        rp.reasonReturn, rp.description)
        from RefoundProduct rp
          join rp.id.purchasedItems pi
          join rp.id.transaction t
          where t.id = :id
          order by pi.productName
          """)
  Page<DetailRefoundProductDTO> findRefoundProductByTransactionId(Pageable pagination, Long id);

  @Query("""
      select rp from RefoundProduct rp
          where rp.id.transaction.id = :id
          """)
  List<RefoundProduct> findAllByTransactionId(Long id);

  @Query("""
      select rp from RefoundProduct rp
          where rp.id.purchasedItems.id = :id
          """)
  Optional<RefoundProduct> findByPurchasedId(Long id);

}
