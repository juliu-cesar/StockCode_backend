package com.stockapi.StockCode.domain.productMovement;

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

@Table(name = "productMovement")
@Entity(name = "ProductMovement")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductMovement {

  @EmbeddedId
  private ProductMovementId id;

  @Enumerated(EnumType.STRING)
  private CategoriesOfMovements categoriesMovements;

  private BigDecimal purchasePrice;

  private Integer amount;

}
