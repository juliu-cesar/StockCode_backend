package com.stockapi.StockCode.domain.stock;

import com.stockapi.StockCode.domain.product.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "stock")
@Entity(name = "Stock")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Stock {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;

  private Integer amount;

  private String location;

  public Stock(AddProductToStockDto dto, Product p) {
    this.product = p;
    this.amount = dto.amount();
    this.location = dto.location();
  }

  public Stock(Product p, Integer a, String l) {
    this.product = p;
    this.amount = a;
    this.location = l;
  }

  public void Update(UpdateStockDto dto){
    if(dto.amount() != null){
      this.amount = dto.amount();
    }
    if(dto.location() != null){
      this.location = dto.location();
    }
  }
}
