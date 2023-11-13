package com.stockapi.StockCode.domain.product.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stockapi.StockCode.domain.product.ProductRepository;
import com.stockapi.StockCode.domain.product.UpdateProductDto;
import com.stockapi.StockCode.infra.exception.EntityNotFoundException;

@Component
public class ValidateIfProductExists implements ValidateProductUpdate {

  @Autowired
  private ProductRepository productRepository;

  @Override
  public void validate(UpdateProductDto dto) {
    if (!productRepository.existsById(Long.valueOf(dto.id()))) {
      throw new EntityNotFoundException("Produto não encontrado. Verifique se ele existe ou se o id esta correto.");
    }
  }
}
