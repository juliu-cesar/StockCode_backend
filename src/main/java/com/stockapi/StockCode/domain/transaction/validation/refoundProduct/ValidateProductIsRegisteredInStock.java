package com.stockapi.StockCode.domain.transaction.validation.refoundProduct;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stockapi.StockCode.domain.stock.StockRepository;
import com.stockapi.StockCode.domain.transaction.purchasedItems.PurchasedItemsRepository;
import com.stockapi.StockCode.domain.transaction.refoundProduct.RefoundDto;
import com.stockapi.StockCode.infra.ValidationException;

@Component
public class ValidateProductIsRegisteredInStock implements ValidateRefoundProduct {

  @Autowired
  private PurchasedItemsRepository purchasedItemsRepository;

  @Autowired
  private StockRepository stockRepository;
  
  @Override
  public void validate(RefoundDto dto) {
    List<Long> stockError = new ArrayList<>();

    dto.listRefoundProduct().forEach(refoundItem ->{
      if(refoundItem.returnedToStock()){
        var purchasedItem = purchasedItemsRepository.findById(refoundItem.purchasedItemId()).get();
        if (!stockRepository.findByProductId(purchasedItem.getProductId()).isPresent()) {
          stockError.add(refoundItem.purchasedItemId());
        }
      }
    });

    if (!stockError.isEmpty()) {
      throw new ValidationException("Produto não cadastrado no estoque.");
    }
  }
  
}
