package com.stockapi.StockCode.domain.transaction.refoundProduct;

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

@Table(name = "refoundProduct")
@Entity(name = "RefoundProduct")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RefoundProduct {

  @EmbeddedId
  private RefoundProductId id;

  private BigDecimal priceRefunded;

  private Integer amountRefunded;

  @Enumerated(EnumType.STRING)
  private ReasonOfReturn reasonReturn;

  private String description;

  public void update(UpdateRefoundDto dto) {
    if (dto.amount() != null) {
      this.amountRefunded += dto.amount();
    }
    if (dto.reason() != null) {
      this.reasonReturn = dto.reason();
    }
    if (dto.description() != null) {
      this.description = dto.description();
    }
  }
}
