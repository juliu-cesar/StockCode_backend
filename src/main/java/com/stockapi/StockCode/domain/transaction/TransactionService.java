package com.stockapi.StockCode.domain.transaction;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockapi.StockCode.domain.product.Product;
import com.stockapi.StockCode.domain.product.ProductRepository;
import com.stockapi.StockCode.domain.stock.Stock;
import com.stockapi.StockCode.domain.stock.StockRepository;
import com.stockapi.StockCode.domain.stock.UpdateStockDto;
import com.stockapi.StockCode.domain.transaction.purchasedItems.CreatePurchasedItemsDto;
import com.stockapi.StockCode.domain.transaction.purchasedItems.PurchasedItems;
import com.stockapi.StockCode.domain.transaction.purchasedItems.PurchasedItemsRepository;
import com.stockapi.StockCode.domain.transaction.refoundProduct.ListRefoundProductDto;
import com.stockapi.StockCode.domain.transaction.refoundProduct.ReasonOfReturn;
import com.stockapi.StockCode.domain.transaction.refoundProduct.RefoundDto;
import com.stockapi.StockCode.domain.transaction.refoundProduct.RefoundProduct;
import com.stockapi.StockCode.domain.transaction.refoundProduct.RefoundProductId;
import com.stockapi.StockCode.domain.transaction.refoundProduct.RefoundProductRepository;
import com.stockapi.StockCode.domain.transaction.refoundProduct.UpdateRefoundDto;
import com.stockapi.StockCode.domain.transaction.validation.purchasedItems.ValidatePurchasedItems;
import com.stockapi.StockCode.domain.transaction.validation.refoundProduct.ValidateRefoundProduct;

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

  @Autowired
  private List<ValidateRefoundProduct> validateRefoundProduct;

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

  public Long refoundProduct(RefoundDto refoundDto) {
    validateRefoundProduct.forEach(v -> v.validate(refoundDto));

    Transaction transaction = transactionRepository.getReferenceById(refoundDto.transactionId());
    List<RefoundProduct> listRefoundByTransaction = refoundProductRepository
        .findAllByTransactionId(refoundDto.transactionId());

    refoundDto.listRefoundProduct().forEach(refoundItem -> {
      PurchasedItems purchasedItem = purchasedItemsRepository.findById(refoundItem.purchasedItemId()).get();

      if (listRefoundByTransaction.isEmpty()) {
        updatePurchasedAndCreateRefound(purchasedItem, refoundItem, transaction);
      } else {
        Optional<RefoundProduct> rp = refoundProductRepository.findByPurchasedId(refoundItem.purchasedItemId());
        if (rp.isPresent()) {
          RefoundProduct refoundProduct = rp.get();
          UpdateRefoundDto updateDto = new UpdateRefoundDto(refoundItem.amountRefunded(), refoundItem.reason(),
              refoundItem.description());

          refoundProduct.update(updateDto);
        } else {
          updatePurchasedAndCreateRefound(purchasedItem, refoundItem, transaction);
        }
      }

      if (refoundItem.reason() == ReasonOfReturn.UNHAPPY_COSTUMER && refoundItem.returnedToStock()) {
        Stock stock = stockRepository.findByProductId(purchasedItem.getProductId()).get();
        Integer stockAmount = stock.getAmount() + refoundItem.amountRefunded();
        var updateStockDto = new UpdateStockDto(Long.valueOf(refoundItem.purchasedItemId()), stockAmount, null);

        stock.Update(updateStockDto);
      }
    });

    return transaction.getId();
  }

  private void updatePurchasedAndCreateRefound(PurchasedItems purchasedItem, ListRefoundProductDto refoundItem,
      Transaction transaction) {
    purchasedItem.refoundProductUpdate(refoundItem);

    RefoundProduct refoundProduct = new RefoundProduct(new RefoundProductId(purchasedItem, transaction),
        purchasedItem.getPurchasePrice(),
        refoundItem.amountRefunded(), refoundItem.reason(), refoundItem.description());
    refoundProductRepository.save(refoundProduct);
  }
}
