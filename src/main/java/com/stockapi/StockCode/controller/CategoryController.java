package com.stockapi.StockCode.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.stockapi.StockCode.domain.category.Category;
import com.stockapi.StockCode.domain.category.CategoryRepository;
import com.stockapi.StockCode.domain.category.CreateCategoryDto;
import com.stockapi.StockCode.domain.category.ListCategoriesDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("category")
public class CategoryController {
  @Autowired
  private CategoryRepository repository;

  @PostMapping
  @Transactional
  public ResponseEntity<?> createCategory(@RequestBody @Valid CreateCategoryDto dto, UriComponentsBuilder uriBuilder) {
    var category = new Category(dto);
    repository.save(category);

    var uri = uriBuilder.path("/category/{id}").buildAndExpand(category.getId()).toUri();

    return ResponseEntity.created(uri).build();
  }

  @GetMapping
  public ResponseEntity<Page<ListCategoriesDto>> listAllCategories(
      @PageableDefault(size = 10, sort = { "categoryName" }) Pageable pagination) {

    var dto = repository.findAll(pagination).map(ListCategoriesDto::new);
    return ResponseEntity.ok(dto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Category> searchById(@PathVariable Long id) {
    var category = repository.findById(id);
    return ResponseEntity.of(category);
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<String> deleteById(@PathVariable Long id) {
    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
