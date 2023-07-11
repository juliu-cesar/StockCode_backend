package com.stockapi.StockCode.domain.transaction.productReturn;

import java.math.BigDecimal;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "productReturn")
@Entity(name = "ProductReturn")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductReturn {

  @EmbeddedId
  private ProductReturnId id;

  private BigDecimal priceRefunded;

  private Integer amountRefunded;

  @Enumerated(EnumType.STRING)
  private ReasonOfReturn reasonReturn;

  private String description;

}
