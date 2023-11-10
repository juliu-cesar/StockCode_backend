package com.stockapi.StockCode.domain.transaction.validation.refoundProduct;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stockapi.StockCode.domain.transaction.purchasedItems.PurchasedItemsRepository;
import com.stockapi.StockCode.domain.transaction.refoundProduct.RefoundDto;
import com.stockapi.StockCode.infra.ValidationException;

@Component
public class ValidateProductBelongToThisTransaction implements ValidateRefoundProduct {

  @Autowired
  private PurchasedItemsRepository purchasedItemsRepository;

  @Override
  public void validate(RefoundDto dto) {
    List<Long> transactionError = new ArrayList<>();

    dto.listRefoundProduct().forEach(refoundItem -> {
      var pi = purchasedItemsRepository.findById(refoundItem.purchasedItemId()).get();
      if (dto.transactionId() != pi.getTransaction().getId()) {
        transactionError.add(pi.getId());
      }
    });
    if (!transactionError.isEmpty()) {
      throw new ValidationException("Não é possivel retornar items de outra transação.");
    }
  }
  
}
