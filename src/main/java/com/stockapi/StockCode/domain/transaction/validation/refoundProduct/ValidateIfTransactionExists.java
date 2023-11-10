package com.stockapi.StockCode.domain.transaction.validation.refoundProduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stockapi.StockCode.domain.transaction.TransactionRepository;
import com.stockapi.StockCode.domain.transaction.refoundProduct.RefoundDto;
import com.stockapi.StockCode.infra.ValidationException;

@Component
public class ValidateIfTransactionExists implements ValidateRefoundProduct{

  @Autowired
  private TransactionRepository transactionRepository;

  @Override
  public void validate(RefoundDto dto) {
    if (!transactionRepository.existsById(dto.transactionId())) {
      throw new ValidationException("Transação não cadastrada.");
    };
  }
  
}
