package com.stockapi.StockCode.domain.transaction.purchasedItems;

import java.math.BigDecimal;

import com.stockapi.StockCode.domain.transaction.Transaction;
import com.stockapi.StockCode.domain.transaction.productReturn.RefoundListDto;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

  // @EmbeddedId
  // private PurchasedItemsId id;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "transaction_id")
  private Transaction transaction;

  private Long productId;
  
  private String productName;

  private String productBrand;

  private String productCategory;

  private BigDecimal purchasePrice;

  private Integer amount;

  private Boolean returned;

  private String description;

  public PurchasedItems(CreatePurchasedItemsDto dto) {
    this.transaction = dto.transaction();
    this.productId = dto.productId();
    this.productName = dto.productName();
    this.productBrand = dto.productBrand();
    this.productCategory = dto.productCategory();
    this.purchasePrice = dto.purchasePrice();
    this.amount = dto.amount();
    this.returned = dto.returned();
    this.description = dto.description();
  }

  public void productReturnUpdate(RefoundListDto prl) {
    this.returned = true;
    this.description = prl.description();
  }

  @Override
  public String toString() {
    return "id= " + id + ", purchase-price= "
        + purchasePrice + ", amount= " + amount + ", total-price= "
        + purchasePrice.multiply(BigDecimal.valueOf(amount));
  }
}
