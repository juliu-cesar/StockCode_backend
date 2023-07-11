package com.stockapi.StockCode.domain.productTransaction;

import java.io.Serializable;

import com.stockapi.StockCode.domain.product.Product;
import com.stockapi.StockCode.domain.transaction.Transaction;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class ProductTransactionId implements Serializable {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product productId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "transaction_id")
  private Transaction transactionId;

  @Override
  public String toString() {
    return "ProductTransactionId [productId=" + productId + ", transactionId=" + transactionId + "]";
  }

}
