package com.stockapi.StockCode.domain.transaction.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stockapi.StockCode.domain.product.ProductRepository;
import com.stockapi.StockCode.domain.transaction.CreateTransactionDto;
import com.stockapi.StockCode.infra.ValidationException;

@Component
public class ValidateThatProductsAreRegistered implements ValidateBuyProduct {
  @Autowired
  private ProductRepository repositoryProduct;

  @Override
  public void validate(CreateTransactionDto dto) {
    List<String> idError = new ArrayList<>();
    
    dto.productList().forEach(p -> {
      if (!repositoryProduct.existsById(Long.valueOf(p.id()))) {
        idError.add(String.valueOf(p.id()));
      }
    });

    if (!idError.isEmpty()) {
      throw new ValidationException("Produto não cadastrado ou identificadores incorretos: " + idError);
    }
  }
}
