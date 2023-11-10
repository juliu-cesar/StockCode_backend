package com.stockapi.StockCode.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.stockapi.StockCode.domain.transaction.CreateTransactionDto;
import com.stockapi.StockCode.domain.transaction.DetailRefoundProductDTO;
import com.stockapi.StockCode.domain.transaction.DetailTransactionProductDTO;
import com.stockapi.StockCode.domain.transaction.ListTransactionDto;
import com.stockapi.StockCode.domain.transaction.TransactionRepository;
import com.stockapi.StockCode.domain.transaction.TransactionService;
import com.stockapi.StockCode.domain.transaction.refoundProduct.RefoundDto;
import com.stockapi.StockCode.domain.transaction.refoundProduct.RefoundProductRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("transaction")
public class TransactionController {

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private RefoundProductRepository refoundProductRepository;

  @PostMapping
  @Transactional
  public ResponseEntity<URI> createTransaction(@RequestBody @Valid CreateTransactionDto createTransactionDto,
      UriComponentsBuilder uriBuilder) {
    var transactionId = transactionService.purchaseProduct(createTransactionDto);
    var uri = uriBuilder.path("/transaction/{id}").buildAndExpand(transactionId).toUri();

    return ResponseEntity.created(uri).build();
  }

  @PutMapping
  @Transactional
  public ResponseEntity<URI> productReturn(@RequestBody @Valid RefoundDto productReturnList,
      UriComponentsBuilder uriBuilder) {
    var refoundId = transactionService.refoundProduct(productReturnList);
    var uri = uriBuilder.path("/transaction/refound/{id}").buildAndExpand(refoundId).toUri();

    return ResponseEntity.created(uri).build();
  }

  @GetMapping
  public ResponseEntity<Page<ListTransactionDto>> listAllTransactions(
      @PageableDefault(size = 20) Pageable pagination) {

    var dto = transactionRepository.findAllWithPrice(pagination);

    return ResponseEntity.ok(dto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Page<DetailTransactionProductDTO>> detailTransaction(
      @PageableDefault(size = 10) Pageable pagination,
      @PathVariable Long id) {

    var dto = transactionRepository.findTransactionAndDetailIt(pagination, id);

    return ResponseEntity.ok(dto);
  }

  @GetMapping("/refound/{id}")
  public ResponseEntity<Page<DetailRefoundProductDTO>> detailRefoundProduct(
      @PageableDefault(size = 10) Pageable pagination,
      @PathVariable Long id) {

    var dto = refoundProductRepository.findRefoundProductByTransactionId(pagination, id);

    return ResponseEntity.ok(dto);
  }

}
