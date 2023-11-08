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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.stockapi.StockCode.domain.product.ProductRepository;
import com.stockapi.StockCode.domain.stock.AddProductToStockDto;
import com.stockapi.StockCode.domain.stock.DetailStockDto;
import com.stockapi.StockCode.domain.stock.ListStockDto;
import com.stockapi.StockCode.domain.stock.Stock;
import com.stockapi.StockCode.domain.stock.StockRepository;
import com.stockapi.StockCode.domain.stock.UpdateStockDto;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("stock")
public class StockController {

  @Autowired
  private StockRepository repositoryStock;

  @Autowired
  private ProductRepository repositoryProduct;

  @PostMapping
  @Transactional
  public ResponseEntity<?> addProductToStock(@RequestBody @Valid AddProductToStockDto dto,
      UriComponentsBuilder uriBuilder) {
    if (repositoryStock.findByProductId(Long.valueOf(dto.productId())).isPresent()){
      return ResponseEntity.badRequest().body("Produto já cadastrado no estoque.");
    }
    var product = repositoryProduct.getReferenceById(Long.valueOf(dto.productId()));
    Stock stock = new Stock(dto, product);
    repositoryStock.save(stock);

    var uri = uriBuilder.path("/stock/{id}").buildAndExpand(product.getId()).toUri();

    return ResponseEntity.created(uri).build();
  }

  @GetMapping
  public ResponseEntity<Page<ListStockDto>> listAllStock(@PageableDefault(size = 10) Pageable pagination) {
    var page = repositoryStock.findAll(pagination).map(ListStockDto::new);
    return ResponseEntity.ok(page);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DetailStockDto> searchById(@PathVariable Long id) {
    var stock = repositoryStock.findByProductId(id).map(DetailStockDto::new);
    return ResponseEntity.of(stock);
  }

  @PutMapping
  @Transactional
  public ResponseEntity<String> updateStock(@RequestBody @Valid UpdateStockDto dto) {
    var stock = repositoryStock.findByProductId(dto.id()).get();
    stock.Update(dto);
    return ResponseEntity.ok("Estoque do produto atualizado com sucesso.");
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<String> deleteStock(@PathVariable Long id) {
    repositoryStock.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
