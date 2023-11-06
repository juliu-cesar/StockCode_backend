package com.stockapi.StockCode.domain.transaction.purchasedItems;

import java.math.BigDecimal;

import com.stockapi.StockCode.domain.transaction.productReturn.RefoundListDto;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "purchasedItems")
@Entity(name = "PurchasedItems")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class PurchasedItems {

  @EmbeddedId
  private PurchasedItemsId id;

  private BigDecimal purchasePrice;

  private Integer amount;

  private Boolean returned;

  private String description;

  public void productReturnUpdate(RefoundListDto prl) {
    this.returned = true;
    this.description = prl.description();
  }

  @Override
  public String toString() {
    return "id= " + id + ", purchase-price= "
        + purchasePrice + ", amount= " + amount + ", total-price= " + purchasePrice.multiply(BigDecimal.valueOf(amount));
  }
}
