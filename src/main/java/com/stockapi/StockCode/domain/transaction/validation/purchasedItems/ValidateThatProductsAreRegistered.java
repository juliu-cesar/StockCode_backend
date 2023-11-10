package com.stockapi.StockCode.domain.transaction.validation.purchasedItems;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stockapi.StockCode.domain.product.ProductRepository;
import com.stockapi.StockCode.domain.transaction.CreateTransactionDto;
import com.stockapi.StockCode.infra.ValidationException;

@Component
public class ValidateThatProductsAreRegistered implements ValidatePurchasedItems {

  @Autowired
  private ProductRepository repository;

  @Override
  public void validate(CreateTransactionDto dto) {
    List<String> idError = new ArrayList<>();
    
    dto.productList().forEach(p -> {
      if (!repository.existsById(Long.valueOf(p.id()))) {
        idError.add(p.id());
      }
    });

    if (!idError.isEmpty()) {
      throw new ValidationException("Produto não cadastrado ou identificadores incorretos: " + idError);
    }
  }
}
