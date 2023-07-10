package com.stockapi.StockCode.controller;

import java.util.List;

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

import com.stockapi.StockCode.domain.product.Product;
import com.stockapi.StockCode.domain.product.ProductListDto;
import com.stockapi.StockCode.domain.product.ProductRepository;
import com.stockapi.StockCode.domain.productTransaction.CategoriesOfMovements;
import com.stockapi.StockCode.domain.productTransaction.ProductTransaction;
import com.stockapi.StockCode.domain.productTransaction.ProductTransactionId;
import com.stockapi.StockCode.domain.productTransaction.ProductTransactionRepository;
import com.stockapi.StockCode.domain.transaction.CreateTransactionDto;
import com.stockapi.StockCode.domain.transaction.DetailTransactionDTO;
import com.stockapi.StockCode.domain.transaction.ListTransactionDto;
import com.stockapi.StockCode.domain.transaction.ProductReturnDto;
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
  private ProductTransactionRepository repositoryPT;

  @PostMapping
  @Transactional
  public void createTransaction(@RequestBody @Valid CreateTransactionDto createTransactionDto) {
    var transaction = new Transaction(createTransactionDto);
    repositoryT.save(transaction);
    
    List<ProductListDto> productList = createTransactionDto.productList();
    productList.forEach(productDto -> {
      var product = repositoryP.getReferenceById(productDto.id());
      var productTransaction = new ProductTransaction(new ProductTransactionId(product, transaction),
          CategoriesOfMovements.SALE, product.getPrice(), productDto.amount(), null, null);

      repositoryPT.save(productTransaction);
    });
  }
  
  @GetMapping
  public ResponseEntity<Page<ListTransactionDto>> listAllTransactions(
    @PageableDefault(size = 10) Pageable pagination) {
      
      var dto = repositoryT.findAllWithPrice(pagination);

      return ResponseEntity.ok(dto);
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<Page<DetailTransactionDTO>> detailTransaction(@PageableDefault(size = 10) Pageable pagination,
      @PathVariable Long id) {

    var dto = repositoryT.findTransactionAndDetailIt(pagination, id);
    
    return ResponseEntity.ok(dto);
  }
  
  @PutMapping
  @Transactional
  public void productReturn(@RequestBody @Valid ProductReturnDto productReturnList){
    Transaction transaction = repositoryT.getReferenceById(productReturnList.transactionId());
    
    productReturnList.productReturnList().forEach(dto ->{
      
      Product product = repositoryP.getReferenceById(dto.productId());
      var ptId = new ProductTransactionId(product, transaction);
      System.out.println("\n ptId: "+ ptId);
      var productTransaction = repositoryPT.findById(ptId);
      System.out.println("\n pt: "+ productTransaction);
      
      // System.out.println("\n reason: "+dto.reason());
      
      System.out.println("\n Antes de efetuar o update");
      productTransaction.productReturnUpdate(dto);
    });


  }

}
