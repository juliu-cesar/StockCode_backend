package com.stockapi.StockCode.domain.productTransaction;

import java.math.BigDecimal;

import com.stockapi.StockCode.domain.product.ProductReturnListDto;
import com.stockapi.StockCode.domain.product.ReasonOfReturn;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "productTransaction")
@Entity(name = "ProductTransaction")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductTransaction {

  @EmbeddedId
  private ProductTransactionId id;

  @Enumerated(EnumType.STRING)
  private CategoriesOfMovements categoriesMovements;

  private BigDecimal purchasePrice;

  private Integer amount;

  private ReasonOfReturn reasonReturn;
  
  private String description;

  public void productReturnUpdate(ProductReturnListDto prl) {
    this.categoriesMovements = CategoriesOfMovements.RETURN;
    this.reasonReturn = prl.reason();
    this.description = prl.description();
  } 

  @Override
  public String toString() {
    return "ProductTransaction [id= " + id + ", categoriesMovements= " + categoriesMovements + ", purchasePrice= "
        + purchasePrice + ", amount= " + amount + ", totalPrice= " + purchasePrice.multiply(BigDecimal.valueOf(amount)) + "]";
  }


}
