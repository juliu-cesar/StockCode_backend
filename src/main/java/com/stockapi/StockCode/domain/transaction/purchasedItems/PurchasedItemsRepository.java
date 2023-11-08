package com.stockapi.StockCode.domain.transaction.purchasedItems;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasedItemsRepository extends JpaRepository<PurchasedItems, Long> {

  // TOTAL VALUE SOLD PER BRAND
  // select sum(subquery.total_price) as total_sum 
    // from (select SUM(purchase_price * amount) as total_price 
          // from purchased_items as pi join product as p on pi.product_id = p.id 
          // where p.brand_id = [BRAND-ID]
          // group by pi.product_id) as subquery;
}
