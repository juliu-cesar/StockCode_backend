package com.stockapi.StockCode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockapi.StockCode.domain.product.ListByBrandDto;
import com.stockapi.StockCode.domain.product.ListByRangePriceDto;
import com.stockapi.StockCode.domain.product.ListProductsDto;
import com.stockapi.StockCode.domain.product.ProductRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("product")
public class ProductController {
  @Autowired
  private ProductRepository repository;

  @GetMapping
  public ResponseEntity<Page<ListProductsDto>> ListAllBrands(
      @PageableDefault(size = 10, sort = { "productName" }) Pageable pagination) {

    var dto = repository.findAll(pagination).map(ListProductsDto::new);
    return ResponseEntity.ok(dto);
  }

  @GetMapping("/range")
  public ResponseEntity<Page<ListProductsDto>> ListByRange(
      @PageableDefault(size = 10, sort = { "price" }) Pageable pagination,
      @RequestBody @Valid ListByRangePriceDto rangePrice) {

    var dto = repository.findAllByPriceBetween(pagination, rangePrice.lowestPrice(), rangePrice.biggestPrice())
        .map(ListProductsDto::new);
    return ResponseEntity.ok(dto);
  }

  @GetMapping("/brand")
  public ResponseEntity<Page<ListProductsDto>> ListByBrand(
      @PageableDefault(size = 10, sort = { "productName" }) Pageable pagination,
      @RequestBody @Valid ListByBrandDto brandDto) {

    var dto = repository.findAllByBrand(pagination, brandDto.brandId()).map(ListProductsDto::new);
    return ResponseEntity.ok(dto);
  }

}
