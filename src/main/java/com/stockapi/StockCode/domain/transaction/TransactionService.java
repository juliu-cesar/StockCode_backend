package com.stockapi.StockCode.domain.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockapi.StockCode.domain.product.Product;
import com.stockapi.StockCode.domain.product.ProductRepository;
import com.stockapi.StockCode.domain.transaction.buyProduct.BuyProduct;
import com.stockapi.StockCode.domain.transaction.buyProduct.BuyProductId;
import com.stockapi.StockCode.domain.transaction.buyProduct.BuyProductRepository;
import com.stockapi.StockCode.domain.transaction.productReturn.ProductReturn;
import com.stockapi.StockCode.domain.transaction.productReturn.ProductReturnId;
import com.stockapi.StockCode.domain.transaction.productReturn.ProductReturnRepository;
import com.stockapi.StockCode.domain.transaction.productReturn.RefoundDto;

@Service
public class TransactionService {

  @Autowired
  private TransactionRepository repositoryTransaction;

  @Autowired
  private ProductRepository repositoryProduct;

  @Autowired
  private BuyProductRepository repositoryBuyProduct;

  @Autowired
  private ProductReturnRepository repositoryProductReturn;

  public Long purchaseProduct(CreateTransactionDto createTransactionDto) {
    
    Transaction transaction = new Transaction(createTransactionDto);
    repositoryTransaction.save(transaction);

    createTransactionDto.productList().forEach(dto -> {
      var product = repositoryProduct.getReferenceById(dto.id());
      var buyProduct = new BuyProduct(new BuyProductId(product, transaction), product.getPrice(),
          dto.amount(), false, dto.description());

      repositoryBuyProduct.save(buyProduct);
    });

    return transaction.getId();
  }

  public Long refoundProduct(RefoundDto productReturnList) {
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

    return transaction.getId();
  }
}
