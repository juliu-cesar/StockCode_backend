package com.stockapi.StockCode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.stockapi.StockCode.domain.brand.Brand;
import com.stockapi.StockCode.domain.brand.BrandRepository;
import com.stockapi.StockCode.domain.brand.CreateBrandDto;
import com.stockapi.StockCode.domain.brand.ListBrandsDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("brand")
public class BrandController {

  @Autowired
  private BrandRepository repository;

  @PostMapping
  public ResponseEntity<?> createBrand(@RequestBody @Valid CreateBrandDto dto, UriComponentsBuilder uriBuilder) {
    var brand = new Brand(dto);
    repository.save(brand);

    var uri = uriBuilder.path("/brand/{id}").buildAndExpand(brand.getId()).toUri();

    return ResponseEntity.created(uri).build();
  }

  @GetMapping
  public ResponseEntity<Page<ListBrandsDto>> listAllBrands(
      @PageableDefault(size = 10, sort = { "brandName" }) Pageable pagination) {

    var dto = repository.findAll(pagination).map(ListBrandsDto::new);
    return ResponseEntity.ok(dto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Brand> searchById(@PathVariable Long id) {
    var brand = repository.findById(id);
    return ResponseEntity.of(brand);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteById(@PathVariable Long id) {
    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
