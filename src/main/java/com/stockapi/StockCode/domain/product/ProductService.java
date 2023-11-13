package com.stockapi.StockCode.domain.product;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockapi.StockCode.domain.brand.Brand;
import com.stockapi.StockCode.domain.brand.BrandRepository;
import com.stockapi.StockCode.domain.category.Category;
import com.stockapi.StockCode.domain.category.CategoryRepository;
import com.stockapi.StockCode.domain.product.validation.ValidateProductUpdate;
import com.stockapi.StockCode.domain.stock.StockRepository;
import com.stockapi.StockCode.infra.exception.EntityNotFoundException;

import jakarta.validation.ValidationException;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private BrandRepository brandRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private StockRepository stockRepository;

  @Autowired
  private List<ValidateProductUpdate> validateProductUpdate;

  public Long create(CreateProductDto dto) {
    var brand = brandRepository.getReferenceById(Long.valueOf(dto.brandId()));
    var category = categoryRepository.getReferenceById(Long.valueOf(dto.categoryId()));
    var product = new Product(dto, brand, category);
    productRepository.save(product);

    return product.getId();
  }

  public ListProductsDto searchProductById(Long id) {
    Product product;
    try {
      product = productRepository.findById(id).get();
    } catch (Exception e) {
      throw new EntityNotFoundException(
          "Não foi possivel encontrar o produto. Verifique se o produto existe ou se o id esta correto.");
    }
    var list = new ListProductsDto(product);
    return list;
  }

  public void update(UpdateProductDto dto) {
    validateProductUpdate.forEach(v -> v.validate(dto));

    Optional<Brand> brand = (dto.brandId() != null) ? brandRepository.findById(dto.brandId()) : Optional.empty();
    Optional<Category> category = (dto.categoryId() != null) ? categoryRepository.findById(dto.categoryId())
        : Optional.empty();

    var product = productRepository.findById(Long.valueOf(dto.id())).get();
    product.update(dto, brand, category);
  }

  public void delete(Long id) {
    if (stockRepository.findByProductId(id).isPresent()) {
      throw new ValidationException("É preciso remover o produto do estoque antes de tentar excluí-lo.");
    }
    productRepository.deleteById(id);
  }
}
