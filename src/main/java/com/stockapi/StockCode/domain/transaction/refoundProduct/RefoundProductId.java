package com.stockapi.StockCode.domain.transaction.refoundProduct;

import java.io.Serializable;

import com.stockapi.StockCode.domain.transaction.Transaction;
import com.stockapi.StockCode.domain.transaction.purchasedItems.PurchasedItems;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RefoundProductId implements Serializable {

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "purchased_items_id")
  private PurchasedItems purchasedItems;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "transaction_id")
  private Transaction transaction;

}
