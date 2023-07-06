package com.stockapi.StockCode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockapi.StockCode.domain.category.CategoryRepository;
import com.stockapi.StockCode.domain.category.ListCategoriesDto;

@RestController
@RequestMapping("category")
public class CategoryController {
  @Autowired
  private CategoryRepository repository;

  @GetMapping
  public ResponseEntity<Page<ListCategoriesDto>> listAllCategories(
      @PageableDefault(size = 10, sort = { "categoryName" }) Pageable pagination) {

    var dto = repository.findAll(pagination).map(ListCategoriesDto::new);
    return ResponseEntity.ok(dto);
  }
}
