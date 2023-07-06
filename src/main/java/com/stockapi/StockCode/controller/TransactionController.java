package com.stockapi.StockCode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockapi.StockCode.domain.transaction.ListTransactionDto;
import com.stockapi.StockCode.domain.transaction.TransactionRepository;

@RestController
@RequestMapping("transaction")
public class TransactionController {

  @Autowired
  private TransactionRepository repository;

  @GetMapping
  public ResponseEntity<Page<ListTransactionDto>> ListAllBrands(
      @PageableDefault(size = 10, sort = { "date" }) Pageable pagination) {

    var dto = repository.findAll(pagination).map(ListTransactionDto::new);
    return ResponseEntity.ok(dto);
  }
}
