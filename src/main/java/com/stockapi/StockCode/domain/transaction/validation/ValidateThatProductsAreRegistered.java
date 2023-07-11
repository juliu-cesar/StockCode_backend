package com.stockapi.StockCode.domain.transaction.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stockapi.StockCode.domain.product.ProductRepository;
import com.stockapi.StockCode.domain.transaction.CreateTransactionDto;
import com.stockapi.StockCode.domain.transaction.ProductListDto;

@Component
public class ValidateThatProductsAreRegistered implements ValidateBuyProduct {
    @Autowired
  private ProductRepository repositoryProduct;

  @Override
  public void validate(CreateTransactionDto dto) {
    List<String> idError;
    List<ProductListDto> productList = dto.productList();
    
    productList.forEach(p -> {
      if(!repositoryProduct.existsById(p.id())){
        idError.add(String.valueOf(p.id()));
      }
    });
  }
  
}
