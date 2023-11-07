package com.stockapi.StockCode.domain.transaction.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stockapi.StockCode.domain.stock.StockRepository;
import com.stockapi.StockCode.domain.transaction.CreateTransactionDto;
import com.stockapi.StockCode.infra.ValidationException;

@Component
public class ValidatePurchaseQuantityIsGreaterThanStockQuantity implements ValidatePurchasedItems  {

  @Autowired
  private StockRepository repository;

  @Override
  public void validate(CreateTransactionDto dto) {
    List<String> amountError = new ArrayList<>();
    
    dto.productList().forEach(p -> {
      var product = repository.findByProductId(Long.valueOf(p.id()));
      if(product.get().getAmount() < p.amount()){
        amountError.add(p.id());
      }
    });

    if(!amountError.isEmpty()){
      throw new ValidationException("Quantidade do produto superior a quantidade em estoque: " + amountError);
    }
  }
  
}
