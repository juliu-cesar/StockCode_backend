package com.stockapi.StockCode.domain.product.validation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stockapi.StockCode.domain.brand.Brand;
import com.stockapi.StockCode.domain.brand.BrandRepository;
import com.stockapi.StockCode.domain.product.UpdateProductDto;
import com.stockapi.StockCode.infra.exception.EntityNotFoundException;
import com.stockapi.StockCode.infra.exception.ValidationException;

@Component
public class ValidateBrandIExists implements ValidateProductUpdate {

  @Autowired
  private BrandRepository brandRepository;

  @Override
  public void validate(UpdateProductDto dto) {
    var id = dto.brandId();
    if (id != null) {
      if (Long.toString(id).length() != 4) {
        throw new ValidationException("Identificador da marca deve conter 4 digitos");
      }
      
      Optional<Brand> brand = brandRepository.findById(id);
      
      if (!brand.isPresent()) {
        throw new EntityNotFoundException("Marca não encontrada.");
      }
    }
  }

}
