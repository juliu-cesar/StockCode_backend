package com.stockapi.StockCode.domain.category;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "category")
@Entity(name = "Category")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category {

  @Id
  private Long id;

  private String categoryName;

  @Override
  public String toString() {
    return "Id: "+id+", category-name: "+categoryName;
  }

}