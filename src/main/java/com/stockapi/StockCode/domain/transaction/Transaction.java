package com.stockapi.StockCode.domain.transaction;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "transaction")
@Entity(name = "Transaction")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transaction {

  @Id
  private Long id;

  private LocalDateTime date;

  private String client;

  private String description;

  public Transaction(CreateTransactionDto dto) {
    this.date = dto.date();
    this.client = dto.client();
    this.description = dto.description();
  }

  @Override
  public String toString() {
    return "Transaction [id=" + id + ", date=" + date + ", client=" + client + ", description=" + description + "]";
  }

}
