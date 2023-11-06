package com.stockapi.StockCode.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockapi.StockCode.domain.brand.Brand;
import com.stockapi.StockCode.domain.brand.BrandRepository;
import com.stockapi.StockCode.domain.category.Category;
import com.stockapi.StockCode.domain.category.CategoryRepository;
import com.stockapi.StockCode.domain.fillTables.FillTablesDto;
import com.stockapi.StockCode.domain.product.Product;
import com.stockapi.StockCode.domain.product.ProductRepository;
import com.stockapi.StockCode.domain.stock.StockRepository;
import com.stockapi.StockCode.domain.transaction.Transaction;
import com.stockapi.StockCode.domain.transaction.TransactionRepository;
import com.stockapi.StockCode.domain.transaction.purchasedItems.PurchasedItems;
import com.stockapi.StockCode.domain.transaction.purchasedItems.PurchasedItemsId;
import com.stockapi.StockCode.domain.transaction.purchasedItems.PurchasedItemsRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("fill-all-tables")
public class FillAllTablesController {

  @Autowired
  private BrandRepository brandRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private StockRepository stockRepository;

  @Autowired
  private PurchasedItemsRepository purchasedItemsRepository;

  private static final String BRAND_CSV = "/table-brand.csv";
  private static final String CATEGORY_CSV = "/table-category.csv";
  private static final String PRODUCT_CSV = "/table-product.csv";
  private static final String STOCK_CSV = "/table-stock.csv";
  private static final String TRANSACTION_CSV = "/table-transaction-clients.csv";
  private static final String COMMA_DELIMITER = ",";

  @PostMapping
  public void fillAllTables() {
    try {
      // readCsv(getClass().getResourceAsStream(BRAND_CSV),
      // "com.stockapi.StockCode.domain.brand.Brand", brandRepository);
      // readCsv(getClass().getResourceAsStream(CATEGORY_CSV),
      // "com.stockapi.StockCode.domain.category.Category",
      // categoryRepository);
      // readCsv(getClass().getResourceAsStream(PRODUCT_CSV),
      // "com.stockapi.StockCode.domain.product.Product", productRepository);
      // readCsv(getClass().getResourceAsStream(STOCK_CSV),
      // "com.stockapi.StockCode.domain.stock.Stock", stockRepository);
      // createTransactions();
      createPurchasedItems();
    } catch (Exception e) {
      throw new RuntimeException("Failed to fill tables: ", e);
    }
    System.out.println("\n ########## the tables have been filled  ##########");
  }

  private <T> void readCsv(InputStream inputStream, String className, JpaRepository<T, Long> repository)
      throws Exception {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    String line;
    Integer count = 0;
    while ((line = bufferedReader.readLine()) != null) {
      String[] values = line.split(COMMA_DELIMITER);
      if (count < 1) {
        count += 1;
        continue;
      }
      count += 1;
      Object entity = createEntity(className, values);
      repository.save((T) entity);
    }
  }

  private Object createEntity(String className, String[] values) throws Exception {
    Class<?> entityClass = Class.forName(className);
    Object entity = entityClass.getDeclaredConstructor().newInstance();
    Field[] fields = entityClass.getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      Field field = fields[i];
      field.setAccessible(true);
      String value = values[i];
      if (field.getType() == Long.class) {
        field.set(entity, Long.valueOf(value));
      } else if (field.getType() == String.class) {
        field.set(entity, value);
      } else if (field.getType() == LocalDateTime.class) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        field.set(entity, LocalDateTime.parse(value, formatter));
      } else if (field.getType() == BigDecimal.class) {
        field.set(entity, new BigDecimal(value).setScale(2));
      } else if (field.getType() == Integer.class) {
        field.set(entity, Integer.valueOf(value));
      } else if (field.getType() == Brand.class) {
        field.set(entity, createJoin(brandRepository, value));
      } else if (field.getType() == Category.class) {
        field.set(entity, createJoin(categoryRepository, value));
      } else if (field.getType() == Product.class) {
        field.set(entity, createJoin(productRepository, value));
      }
    }
    return entity;
  }

  private <T> Object createJoin(JpaRepository<T, Long> repository, String value) {
    Object entity = repository.findById(Long.valueOf(value)).get();
    return entity;
  }

  private void createTransactions() throws IOException {
    LocalDateTime dateTime = LocalDateTime.of(LocalDate.now().minusDays(35), LocalTime.of(9, 12, 0));
    Long addMinutes = Long.valueOf("89");
    Integer transactionId = 10000000;

    InputStream inputStream = getClass().getResourceAsStream(TRANSACTION_CSV);
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    String line;

    for (int i = 0; i <= 35; i++) {

      while (dateTime.getHour() < 18) {
        line = bufferedReader.readLine();
        String[] values = line.split(COMMA_DELIMITER);

        Transaction tr = new Transaction(Long.valueOf(transactionId), dateTime, values[0], "");
        transactionRepository.save(tr);

        transactionId++;
        dateTime = dateTime.plusMinutes(addMinutes);
      }
      dateTime = dateTime.plusHours(15);
    }
  }

  private void createPurchasedItems() {
    List<Transaction> transactions = transactionRepository.findAll();

    transactions.forEach(transaction -> {
      var numberOfProduct = (int) Math.ceil(Math.random() * 6);
      List<Product> products = productRepository.selectRandomProducts(numberOfProduct);

      products.forEach(product -> {
        var pId = new PurchasedItemsId(product, transaction);
        Integer amount = selectAmount(product.getPrice());

        purchasedItemsRepository.save(new PurchasedItems(pId, product.getPrice(), amount, false, ""));
      });

    });
  }

  private Integer selectAmount(BigDecimal price){
    if (price.compareTo(new BigDecimal("3")) < 0) {
      return (int) Math.ceil(Math.random() * 100);
    } else if(price.compareTo(new BigDecimal("30")) < 0){
      return (int) Math.ceil(Math.random() * 10);
    } else if(price.compareTo(new BigDecimal("60")) < 0){
      return (int) Math.ceil(Math.random() * 5);
    }
    return (int) Math.ceil(Math.random() * 2);
  }
}

