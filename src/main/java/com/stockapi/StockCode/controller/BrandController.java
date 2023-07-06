package com.stockapi.StockCode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockapi.StockCode.domain.brand.BrandRepository;
import com.stockapi.StockCode.domain.brand.ListBrandsDto;

@RestController
@RequestMapping("brand")
public class BrandController {

  @Autowired
  private BrandRepository repository;

  @GetMapping
  public ResponseEntity<Page<ListBrandsDto>> ListAllBrands(
      @PageableDefault(size = 10, sort = { "brandName" }) Pageable pagination) {

    var dto = repository.findAll(pagination).map(ListBrandsDto::new);
    return ResponseEntity.ok(dto);
  }
}
