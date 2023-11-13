package com.stockapi.StockCode.domain.transaction.validation.refoundProduct;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stockapi.StockCode.domain.transaction.purchasedItems.PurchasedItemsRepository;
import com.stockapi.StockCode.domain.transaction.refoundProduct.RefoundDto;
import com.stockapi.StockCode.infra.exception.ValidationException;

@Component
public class ValidateIfPurchasedItemExists implements ValidateRefoundProduct {

  @Autowired
  private PurchasedItemsRepository purchasedItemsRepository;

  @Override
  public void validate(RefoundDto dto) {
    List<Long> purchasedError = new ArrayList<>();

    dto.listRefoundProduct().forEach(refoundItem -> {
      if(!purchasedItemsRepository.existsById(refoundItem.purchasedItemId())){
        purchasedError.add(refoundItem.purchasedItemId());
      }
    });

    if (!purchasedError.isEmpty()) {
      throw new ValidationException("Identificador da transação do produto incorreto.");
    }
  }
  
  
}
