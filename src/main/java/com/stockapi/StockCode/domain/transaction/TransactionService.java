package com.stockapi.StockCode.domain.transaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockapi.StockCode.domain.product.Product;
import com.stockapi.StockCode.domain.product.ProductRepository;
import com.stockapi.StockCode.domain.stock.Stock;
import com.stockapi.StockCode.domain.stock.StockRepository;
import com.stockapi.StockCode.domain.stock.UpdateStockDto;
import com.stockapi.StockCode.domain.transaction.RefoundProduct.RefoundDto;
import com.stockapi.StockCode.domain.transaction.RefoundProduct.RefoundProduct;
import com.stockapi.StockCode.domain.transaction.RefoundProduct.RefoundProductId;
import com.stockapi.StockCode.domain.transaction.RefoundProduct.RefoundProductRepository;
import com.stockapi.StockCode.domain.transaction.purchasedItems.CreatePurchasedItemsDto;
import com.stockapi.StockCode.domain.transaction.purchasedItems.PurchasedItems;
import com.stockapi.StockCode.domain.transaction.purchasedItems.PurchasedItemsRepository;
import com.stockapi.StockCode.domain.transaction.validation.ValidatePurchasedItems;

@Service
public class TransactionService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private StockRepository stockRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private PurchasedItemsRepository purchasedItemsRepository;

  @Autowired
  private RefoundProductRepository refoundProductRepository;

  @Autowired
  private List<ValidatePurchasedItems> validatePurchasedItems;

  public Long purchaseProduct(CreateTransactionDto createTransactionDto) {

    validatePurchasedItems.forEach(v -> v.validate(createTransactionDto));

    Transaction transaction = new Transaction(createTransactionDto);
    transactionRepository.save(transaction);

    createTransactionDto.productList().forEach(item -> {
      Product product = productRepository.getReferenceById(Long.valueOf(item.id()));
      Stock stock = stockRepository.findByProductId(Long.valueOf(item.id())).get();
      Integer stockAmount = stock.getAmount() - item.amount();
      var updateStockDto = new UpdateStockDto(Long.valueOf(item.id()), stockAmount, null);

      stock.Update(updateStockDto);

      var dto = new CreatePurchasedItemsDto(transaction, product.getId(),
          product.getProductName(), product.getBrand().getBrandName(),
          product.getCategory().getCategoryName(), product.getPrice(),
          item.amount(), false, item.description());
      purchasedItemsRepository.save(new PurchasedItems(dto));
    });

    return transaction.getId();
  }

  public Long refoundProduct(RefoundDto refound) {
    Transaction transaction = transactionRepository.getReferenceById(refound.transactionId());

    // TODO : apply validation

    refound.listRefoundProduct().forEach(productReturned -> {
      PurchasedItems purchasedItem = purchasedItemsRepository.findById(productReturned.purchasedItemId()).get();
      purchasedItem.refoundProductUpdate(productReturned);

      var productReturn = new RefoundProduct(new RefoundProductId(purchasedItem, transaction),
          purchasedItem.getPurchasePrice(),
          productReturned.amountRefunded(), productReturned.reason(), productReturned.description());
      refoundProductRepository.save(productReturn);
    });

    return transaction.getId();
  }
}
