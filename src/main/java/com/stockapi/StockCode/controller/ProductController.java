package com.stockapi.StockCode.controller;

import java.net.URI;

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

import com.stockapi.StockCode.domain.product.CreateProductDto;
import com.stockapi.StockCode.domain.product.ListByBrandDto;
import com.stockapi.StockCode.domain.product.ListByRangePriceDto;
import com.stockapi.StockCode.domain.product.ListProductsDto;
import com.stockapi.StockCode.domain.product.ProductRepository;
import com.stockapi.StockCode.domain.product.ProductService;
import com.stockapi.StockCode.domain.product.UpdateProductDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("product")
public class ProductController {

  @Autowired
  private ProductService productService;

  @Autowired
  private ProductRepository productRepository;

  @PostMapping
  @Transactional
  public ResponseEntity<URI> createProduct(@RequestBody @Valid CreateProductDto dto,
      UriComponentsBuilder uriBuilder) {
    var id = productService.create(dto);
    var uri = uriBuilder.path("/product/{id}").buildAndExpand(id).toUri();
    return ResponseEntity.created(uri).build();
  }

  @GetMapping
  public ResponseEntity<Page<ListProductsDto>> listAllProducts(
      @PageableDefault(size = 20, sort = { "productName" }) Pageable pagination) {
    var dto = productRepository.findAll(pagination).map(ListProductsDto::new);
    return ResponseEntity.ok(dto);
  }

  @GetMapping("/range")
  public ResponseEntity<Page<ListProductsDto>> listByRange(
      @RequestBody @Valid ListByRangePriceDto rangePrice,
      @PageableDefault(size = 10, sort = { "price" }) Pageable pagination) {
    var dto = productRepository.findAllByPriceBetween(pagination, rangePrice.lowestPrice(), rangePrice.biggestPrice())
        .map(ListProductsDto::new);
    return ResponseEntity.ok(dto);
  }

  @GetMapping("/brand")
  public ResponseEntity<Page<ListProductsDto>> listByBrand(
      @RequestBody @Valid ListByBrandDto brandDto,
      @PageableDefault(size = 10, sort = { "productName" }) Pageable pagination) {
    var dto = productRepository.findAllByBrand(pagination, brandDto.brandId()).map(ListProductsDto::new);
    return ResponseEntity.ok(dto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ListProductsDto> searchById(@PathVariable Long id) {
    var list = productService.searchProductById(id);
    return ResponseEntity.ok(list);
  }

  @PutMapping
  @Transactional
  public ResponseEntity<String> updateProduct(@RequestBody @Valid UpdateProductDto dto) {
    productService.update(dto);
    return ResponseEntity.ok("Produto atualizado com sucesso.");
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
    productService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
