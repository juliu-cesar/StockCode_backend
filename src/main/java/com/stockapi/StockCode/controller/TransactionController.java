package com.stockapi.StockCode.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockapi.StockCode.domain.product.ProductRepository;
import com.stockapi.StockCode.domain.productTransaction.CategoriesOfMovements;
import com.stockapi.StockCode.domain.productTransaction.ProductListDto;
import com.stockapi.StockCode.domain.productTransaction.ProductTransaction;
import com.stockapi.StockCode.domain.productTransaction.ProductTransactionId;
import com.stockapi.StockCode.domain.productTransaction.ProductTransactionRepository;
import com.stockapi.StockCode.domain.transaction.CreateTransactionDto;
import com.stockapi.StockCode.domain.transaction.ListTransactionDto;
import com.stockapi.StockCode.domain.transaction.Transaction;
import com.stockapi.StockCode.domain.transaction.TransactionRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("transaction")
public class TransactionController {

  @Autowired
  private TransactionRepository repositoryT;

  @Autowired
  private ProductRepository repositoryP;

  @Autowired
  private ProductTransactionRepository repositoryM;

  @GetMapping
  public ResponseEntity<List<ListTransactionDto>> listAllTransactions(
      @PageableDefault(size = 10, sort = { "date" }) Pageable pagination) {

    var dto = repositoryT.findAllWithPrice();
    return ResponseEntity.ok(dto);
  }

  @PostMapping
  @Transactional
  public void createTransaction(@RequestBody @Valid CreateTransactionDto createTransactionDto) {
    var transaction = new Transaction(createTransactionDto);
    repositoryT.save(transaction);

    List<ProductListDto> productList = createTransactionDto.productList();
    productList.forEach(productDto -> {
      var product = repositoryP.getReferenceById(productDto.id());
      var productTransaction = new ProductTransaction(new ProductTransactionId(product, transaction),
          CategoriesOfMovements.SALE, product.getPrice(), productDto.amount());

      repositoryM.save(productTransaction);
    });
  }
}
