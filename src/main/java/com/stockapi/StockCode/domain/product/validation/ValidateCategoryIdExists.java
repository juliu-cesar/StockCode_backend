package com.stockapi.StockCode.domain.product.validation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stockapi.StockCode.domain.category.Category;
import com.stockapi.StockCode.domain.category.CategoryRepository;
import com.stockapi.StockCode.domain.product.UpdateProductDto;
import com.stockapi.StockCode.infra.exception.EntityNotFoundException;
import com.stockapi.StockCode.infra.exception.ValidationException;

@Component
public class ValidateCategoryIdExists implements ValidateProductUpdate {

  @Autowired
  private CategoryRepository categoryRepository;

  @Override
  public void validate(UpdateProductDto dto) {
    var id = dto.categoryId();
    if (id != null) {
      if (Long.toString(id).length() != 3) {
        throw new ValidationException("Identificador da categoria deve conter 3 digitos");
      }
      Optional<Category> category = categoryRepository.findById(id);

      if (id != null && !category.isPresent()) {
        throw new EntityNotFoundException("Categoria não encontrada.");
      }
    }
  }

}
