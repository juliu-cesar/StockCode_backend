package com.stockapi.StockCode.domain.transaction.validation.refoundProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stockapi.StockCode.domain.transaction.purchasedItems.PurchasedItems;
import com.stockapi.StockCode.domain.transaction.purchasedItems.PurchasedItemsRepository;
import com.stockapi.StockCode.domain.transaction.refoundProduct.RefoundDto;
import com.stockapi.StockCode.domain.transaction.refoundProduct.RefoundProduct;
import com.stockapi.StockCode.domain.transaction.refoundProduct.RefoundProductRepository;
import com.stockapi.StockCode.infra.exception.ValidationException;

@Component
public class ValidateRefoundQuantityIsGreaterThanPurchasedQuantity implements ValidateRefoundProduct {

  @Autowired
  private PurchasedItemsRepository purchasedItemsRepository;

  @Autowired
  private RefoundProductRepository refoundProductRepository;

  @Override
  public void validate(RefoundDto dto) {
    List<Long> amountError = new ArrayList<>();
    List<RefoundProduct> listRefoundByTransaction = refoundProductRepository
        .findAllByTransactionId(dto.transactionId());

    dto.listRefoundProduct().forEach(refoundItem -> {
      PurchasedItems purchasedItem = purchasedItemsRepository.findById(refoundItem.purchasedItemId()).get();
      if (refoundItem.amountRefunded() > purchasedItem.getAmount()) {
        amountError.add(purchasedItem.getId());
      }

      if (!listRefoundByTransaction.isEmpty()) {
        Optional<RefoundProduct> refound = refoundProductRepository.findByPurchasedId(refoundItem.purchasedItemId());

        if (refound.isPresent()) {
          Integer average = purchasedItem.getAmount() - refound.get().getAmountRefunded();

          if (refoundItem.amountRefunded() > average) {
            amountError.add(purchasedItem.getId());
          }
        }
      }

    });
    if (!amountError.isEmpty()) {
      throw new ValidationException("Quantidade retornada é superior a quantidade comprada.");
    }
  }

}
