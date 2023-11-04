package com.stockapi.StockCode.domain.transaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockapi.StockCode.domain.product.Product;
import com.stockapi.StockCode.domain.product.ProductRepository;
import com.stockapi.StockCode.domain.transaction.productReturn.ProductReturn;
import com.stockapi.StockCode.domain.transaction.productReturn.ProductReturnId;
import com.stockapi.StockCode.domain.transaction.productReturn.ProductReturnRepository;
import com.stockapi.StockCode.domain.transaction.productReturn.RefoundDto;
import com.stockapi.StockCode.domain.transaction.purchasedItems.PurchasedItems;
import com.stockapi.StockCode.domain.transaction.purchasedItems.PurchasedItemsId;
import com.stockapi.StockCode.domain.transaction.purchasedItems.PurchasedItemsRepository;
import com.stockapi.StockCode.domain.transaction.validation.ValidateBuyProduct;

@Service
public class TransactionService {

  @Autowired
  private TransactionRepository repositoryTransaction;

  @Autowired
  private ProductRepository repositoryProduct;

  @Autowired
  private PurchasedItemsRepository repositoryBuyProduct;

  @Autowired
  private ProductReturnRepository repositoryProductReturn;

  @Autowired
  private List<ValidateBuyProduct> validateBuyProduct;

  public Long purchaseProduct(CreateTransactionDto createTransactionDto) {

    validateBuyProduct.forEach(v -> v.validate(createTransactionDto));

    Transaction transaction = new Transaction(createTransactionDto);
    repositoryTransaction.save(transaction);

    createTransactionDto.productList().forEach(dto -> {
      var product = repositoryProduct.getReferenceById(Long.valueOf(dto.id()));
      var buyProduct = new PurchasedItems(new PurchasedItemsId(product, transaction), product.getPrice(),
          dto.amount(), false, dto.description());

      repositoryBuyProduct.save(buyProduct);
    });

    return transaction.getId();
  }

  public Long refoundProduct(RefoundDto productReturnList) {
    Transaction transaction = repositoryTransaction.getReferenceById(productReturnList.transactionId());

    productReturnList.productReturnList().forEach(dto -> {
      Product product = repositoryProduct.getReferenceById(dto.productId());
      var ptId = new PurchasedItemsId(product, transaction);
      var buyProduct = repositoryBuyProduct.findById(ptId);
      buyProduct.productReturnUpdate(dto);

      var productReturn = new ProductReturn(new ProductReturnId(product, transaction), buyProduct.getPurchasePrice(),
          dto.amountRefunded(), dto.reason(), dto.description());
      repositoryProductReturn.save(productReturn);
    });

    return transaction.getId();
  }
}
