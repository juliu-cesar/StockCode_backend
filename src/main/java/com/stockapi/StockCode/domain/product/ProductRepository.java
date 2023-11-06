package com.stockapi.StockCode.domain.product;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Page<Product> findAllByPriceBetween(Pageable pagination, BigDecimal lowestPrice, BigDecimal biggestPrice);

  @Query("""
      select p from Product p
       where p.brand.id = :brandId
       """)
  Page<Product> findAllByBrand(Pageable pagination, Long brandId);

  @Query("""
      select p from Product p
       order by rand()
       limit :numberOfProduct
       """)
  List<Product> selectRandomProducts(Integer numberOfProduct);

}
