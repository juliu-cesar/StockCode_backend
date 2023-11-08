package com.stockapi.StockCode.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.stockapi.StockCode.domain.brand.Brand;
import com.stockapi.StockCode.domain.brand.BrandRepository;
import com.stockapi.StockCode.domain.category.Category;
import com.stockapi.StockCode.domain.category.CategoryRepository;
import com.stockapi.StockCode.domain.product.CreateProductDto;
import com.stockapi.StockCode.domain.product.ListByBrandDto;
import com.stockapi.StockCode.domain.product.ListByRangePriceDto;
import com.stockapi.StockCode.domain.product.ListProductsDto;
import com.stockapi.StockCode.domain.product.Product;
import com.stockapi.StockCode.domain.product.ProductRepository;
import com.stockapi.StockCode.domain.product.UpdateProductDto;
import com.stockapi.StockCode.domain.stock.StockRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("product")
public class ProductController {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private BrandRepository brandRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private StockRepository stockRepository;

  @PostMapping
  @Transactional
  public ResponseEntity<?> createProduct(@RequestBody @Valid CreateProductDto dto,
      UriComponentsBuilder uriBuilder) {
    var brand = brandRepository.getReferenceById(Long.valueOf(dto.brandId()));
    var category = categoryRepository.getReferenceById(Long.valueOf(dto.categoryId()));
    var product = new Product(dto, brand, category);
    productRepository.save(product);

    var uri = uriBuilder.path("/product/{id}").buildAndExpand(product.getId()).toUri();

    return ResponseEntity.created(uri).build();
  }

  @GetMapping
  public ResponseEntity<Page<ListProductsDto>> listAllProducts(
      @PageableDefault(size = 10, sort = { "productName" }) Pageable pagination) {

    var dto = productRepository.findAll(pagination).map(ListProductsDto::new);
    return ResponseEntity.ok(dto);
  }

  @GetMapping("/range")
  public ResponseEntity<Page<ListProductsDto>> listByRange(
      @PageableDefault(size = 10, sort = { "price" }) Pageable pagination,
      @RequestBody @Valid ListByRangePriceDto rangePrice) {

    var dto = productRepository.findAllByPriceBetween(pagination, rangePrice.lowestPrice(), rangePrice.biggestPrice())
        .map(ListProductsDto::new);
    return ResponseEntity.ok(dto);
  }

  @GetMapping("/brand")
  public ResponseEntity<Page<ListProductsDto>> listByBrand(
      @PageableDefault(size = 10, sort = { "productName" }) Pageable pagination,
      @RequestBody @Valid ListByBrandDto brandDto) {

    var dto = productRepository.findAllByBrand(pagination, brandDto.brandId()).map(ListProductsDto::new);
    return ResponseEntity.ok(dto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> searchById(@PathVariable Long id) {
    if (!productRepository.existsById(id)) {
      return ResponseEntity.badRequest().body("Não foi possivel encontrar o produto. Verifique se o produto existe ou se o id esta correto.");
    }
    var product = productRepository.findById(id).get();
    return ResponseEntity.ok(new ListProductsDto(product));
  }

  @PutMapping
  @Transactional
  public ResponseEntity<String> updateProduct(@RequestBody @Valid UpdateProductDto dto) {
    if (dto.price() != null) {
      if (dto.price().compareTo(BigDecimal.ZERO) <= 0) {
        return ResponseEntity.badRequest().body("Preço deve ser um valor maior que zero.");
      }
    }
    Optional<Brand> brand = (dto.brandId() != null) ? brandRepository.findById(dto.brandId()) : Optional.empty();
    Optional<Category> category = (dto.categoryId() != null) ? categoryRepository.findById(dto.categoryId())
        : Optional.empty();

    if (dto.brandId() != null && !brand.isPresent()) {
      return ResponseEntity.badRequest().body("Marca não encontrada.");
    }
    if (dto.categoryId() != null && !category.isPresent()) {
      return ResponseEntity.badRequest().body("Categoria não encontrada.");
    }

    var product = productRepository.findById(Long.valueOf(dto.id())).get();
    product.update(dto, brand, category);

    return ResponseEntity.ok("Produto atualizado com sucesso.");
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
    if (stockRepository.findByProductId(id).isPresent()) {
      return ResponseEntity.badRequest().body("É preciso remover o produto do estoque antes de tentar excluí-lo.");
    }
    productRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

}
