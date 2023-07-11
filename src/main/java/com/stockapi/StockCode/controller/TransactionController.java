package com.stockapi.StockCode.controller;

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
import com.stockapi.StockCode.domain.product.ProductRepository;
import com.stockapi.StockCode.domain.transaction.CreateTransactionDto;
import com.stockapi.StockCode.domain.transaction.DetailProductReturnDTO;
import com.stockapi.StockCode.domain.transaction.DetailTransactionProductDTO;
import com.stockapi.StockCode.domain.transaction.ListTransactionDto;
import com.stockapi.StockCode.domain.transaction.Transaction;
import com.stockapi.StockCode.domain.transaction.TransactionRepository;
import com.stockapi.StockCode.domain.transaction.buyProduct.BuyProduct;
import com.stockapi.StockCode.domain.transaction.buyProduct.BuyProductId;
import com.stockapi.StockCode.domain.transaction.buyProduct.BuyProductRepository;
import com.stockapi.StockCode.domain.transaction.productReturn.ProductReturn;
import com.stockapi.StockCode.domain.transaction.productReturn.ProductReturnId;
import com.stockapi.StockCode.domain.transaction.productReturn.ProductReturnRepository;
import com.stockapi.StockCode.domain.transaction.productReturn.RefoundDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("transaction")
public class TransactionController {

  @Autowired
  private TransactionRepository repositoryTransaction;

  @Autowired
  private ProductRepository repositoryProduct;

  @Autowired
  private BuyProductRepository repositoryBuyProduct;

  @Autowired
  private ProductReturnRepository repositoryProductReturn;

  @PostMapping
  @Transactional
  public void createTransaction(@RequestBody @Valid CreateTransactionDto createTransactionDto) {
    var transaction = new Transaction(createTransactionDto);
    repositoryTransaction.save(transaction);

    createTransactionDto.productList().forEach(dto -> {
      var product = repositoryProduct.getReferenceById(dto.id());
      var buyProduct = new BuyProduct(new BuyProductId(product, transaction), product.getPrice(),
          dto.amount(), false, dto.description());

      repositoryBuyProduct.save(buyProduct);
    });
  }

  @GetMapping
  public ResponseEntity<Page<ListTransactionDto>> listAllTransactions(
      @PageableDefault(size = 10) Pageable pagination) {

    var dto = repositoryTransaction.findAllWithPrice(pagination);

    return ResponseEntity.ok(dto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Page<DetailTransactionProductDTO>> detailTransaction(
      @PageableDefault(size = 10) Pageable pagination,
      @PathVariable Long id) {

    var dto = repositoryTransaction.findTransactionAndDetailIt(pagination, id);

    return ResponseEntity.ok(dto);
  }

  @GetMapping("/refound/{id}")
  public ResponseEntity<Page<DetailProductReturnDTO>> detailProductReturn(
      @PageableDefault(size = 10) Pageable pagination,
      @PathVariable Long id) {

    var dto = repositoryProductReturn.findProductReturnAndDetailIt(pagination, id);

    return ResponseEntity.ok(dto);
  }

  @PutMapping
  @Transactional
  public void productReturn(@RequestBody @Valid RefoundDto productReturnList) {
    Transaction transaction = repositoryTransaction.getReferenceById(productReturnList.transactionId());

    productReturnList.productReturnList().forEach(dto -> {
      Product product = repositoryProduct.getReferenceById(dto.productId());
      var ptId = new BuyProductId(product, transaction);
      var buyProduct = repositoryBuyProduct.findById(ptId);
      buyProduct.productReturnUpdate(dto);

      var productReturn = new ProductReturn(new ProductReturnId(product, transaction), buyProduct.getPurchasePrice(),
          dto.amountRefunded(), dto.reason(), dto.description());
      repositoryProductReturn.save(productReturn);
    });

  }

}
