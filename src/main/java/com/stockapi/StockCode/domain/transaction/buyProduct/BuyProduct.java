package com.stockapi.StockCode.domain.transaction.buyProduct;

import java.math.BigDecimal;

import com.stockapi.StockCode.domain.transaction.productReturn.RefoundListDto;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "buyProduct")
@Entity(name = "BuyProduct")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class BuyProduct {

  @EmbeddedId
  private BuyProductId id;

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
    return "ProductTransaction [id= " + id + ", purchasePrice= "
        + purchasePrice + ", amount= " + amount + ", totalPrice= " + purchasePrice.multiply(BigDecimal.valueOf(amount))
        + "]";
  }
}
