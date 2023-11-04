package com.stockapi.StockCode.domain.brand;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "brand")
@Entity(name = "Brand")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Brand {
  @Id
  private Long id;

  private String brandName;

  @Override
  public String toString() {
    return "Id: "+this.id+", brand name: "+this.brandName;
  }
}
