package com.stockapi.StockCode.utils.fillAllTables;

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
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.stockapi.StockCode.domain.brand.Brand;
import com.stockapi.StockCode.domain.brand.BrandRepository;
import com.stockapi.StockCode.domain.category.Category;
import com.stockapi.StockCode.domain.category.CategoryRepository;
import com.stockapi.StockCode.domain.product.Product;
import com.stockapi.StockCode.domain.product.ProductRepository;
import com.stockapi.StockCode.domain.stock.AddProductToStockDto;
import com.stockapi.StockCode.domain.stock.Stock;
import com.stockapi.StockCode.domain.stock.StockRepository;
import com.stockapi.StockCode.domain.transaction.CreateTransactionDto;
import com.stockapi.StockCode.domain.transaction.Transaction;
import com.stockapi.StockCode.domain.transaction.TransactionRepository;
import com.stockapi.StockCode.domain.transaction.purchasedItems.CreatePurchasedItemsDto;
import com.stockapi.StockCode.domain.transaction.purchasedItems.PurchasedItems;
import com.stockapi.StockCode.domain.transaction.purchasedItems.PurchasedItemsId;
import com.stockapi.StockCode.domain.transaction.purchasedItems.PurchasedItemsRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class FillAllTablesService {

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

  @PersistenceContext
  private EntityManager entityManager;

  private static final String BRAND_CSV = "/table-brand.csv";
  private static final String CATEGORY_CSV = "/table-category.csv";
  private static final String PRODUCT_CSV = "/table-product.csv";
  private static final String TRANSACTION_CSV = "/table-transaction-clients.csv";
  private static final String COMMA_DELIMITER = ",";

  public String fillAllTables() {
    if (!productRepository.findAll().isEmpty()) {
      return "tha tables are filled";
    }
    System.out.println("\n Start process.");
    try {
      readCsv(getClass().getResourceAsStream(BRAND_CSV),
          "com.stockapi.StockCode.domain.brand.Brand", brandRepository);
      readCsv(getClass().getResourceAsStream(CATEGORY_CSV),
          "com.stockapi.StockCode.domain.category.Category",
          categoryRepository);
      readCsv(getClass().getResourceAsStream(PRODUCT_CSV),
          "com.stockapi.StockCode.domain.product.Product", productRepository);
      createStock();
      createTransactions();
      createPurchasedItems();
    } catch (Exception e) {
      throw new RuntimeException("Failed to fill tables: ", e);
    }
    System.out.println("\n Finished fill process");
    return "########## the tables have been filled  ##########";
  }

  public String emptyAllTheTables() {

    purchasedItemsRepository.deleteAll();
    transactionRepository.deleteAll();
    stockRepository.deleteAll();
    productRepository.deleteAll();
    categoryRepository.deleteAll();
    entityManager.createNativeQuery("ALTER TABLE category AUTO_INCREMENT = 100;").executeUpdate();
    brandRepository.deleteAll();
    entityManager.createNativeQuery("ALTER TABLE brand AUTO_INCREMENT = 1000;").executeUpdate();
    return "########## the tables have been cleared ##########";
  }

  private <T> void readCsv(InputStream inputStream, String className, JpaRepository<T, Long> repository)
      throws Exception {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    String line;
    while ((line = bufferedReader.readLine()) != null) {
      String[] values = line.split(COMMA_DELIMITER);
      Object entity = createEntity(className, values);
      repository.save((T) entity);
    }
  }

  private Object createEntity(String className, String[] values) throws Exception {
    Class<?> entityClass = Class.forName(className);
    Object entity = entityClass.getDeclaredConstructor().newInstance();
    Field[] fields = entityClass.getDeclaredFields();
    for (int i = 1; i < fields.length; i++) {
      Field field = fields[i];
      field.setAccessible(true);
      String value = values[i - 1];
      if (field.getType() == String.class) {
        field.set(entity, value);
      } else if (field.getType() == BigDecimal.class) {
        field.set(entity, new BigDecimal(value).setScale(2));
      } else if (field.getType() == Brand.class) {
        field.set(entity, createJoin(brandRepository, value));
      } else if (field.getType() == Category.class) {
        field.set(entity, createJoin(categoryRepository, value));
      }
    }
    return entity;
  }

  private <T> Object createJoin(JpaRepository<T, Long> repository, String value) {
    Object entity = repository.findById(Long.valueOf(value)).get();
    return entity;
  }

  private void createStock() {
    List<Product> productList = productRepository.findAll();

    productList.forEach(product -> {
      Integer amount = getRandomAmount(product.getPrice());
      String location = getRandomLocation(6);
      var stock = new Stock(product, amount, location);
      stockRepository.save(stock);
    });

  }

  private Integer getRandomAmount(BigDecimal price) {
    if (price.compareTo(new BigDecimal("3")) < 0) {
      Integer i = 0;
      while (i < 500) {
        i = (int) Math.ceil(Math.random() * 5000);
      }
      return i;
    } else if (price.compareTo(new BigDecimal("30")) < 0) {
      Integer i = 0;
      while (i < 50) {
        i = (int) Math.ceil(Math.random() * 200);
      }
      return i;
    } else if (price.compareTo(new BigDecimal("100")) < 0) {
      Integer i = 0;
      while (i < 30) {
        i = (int) Math.ceil(Math.random() * 100);
      }
      return i;
    } else if (price.compareTo(new BigDecimal("200")) < 0) {
      Integer i = 0;
      while (i < 15) {
        i = (int) Math.ceil(Math.random() * 30);
      }
      return i;
    }
    return (int) Math.ceil(Math.random() * 20);
  }

  private String getRandomLocation(Integer length) {
    String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    Random random = new Random();
    StringBuilder sb = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      int index = random.nextInt(characters.length());
      sb.append(characters.charAt(index));
    }

    return sb.toString();
  }

  private void createTransactions() throws IOException {
    LocalDateTime dateTime = LocalDateTime.of(LocalDate.now().minusDays(35), LocalTime.of(9, 12, 0));
    Long addMinutes = Long.valueOf("89");

    InputStream inputStream = getClass().getResourceAsStream(TRANSACTION_CSV);
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    String line;

    for (int i = 0; i <= 35; i++) {

      while (dateTime.getHour() < 18) {
        line = bufferedReader.readLine();
        String[] values = line.split(COMMA_DELIMITER);

        Transaction tr = new Transaction(dateTime, values[0], "");
        transactionRepository.save(tr);

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
        Integer amount = selectAmount(product.getPrice());

        var dto = new CreatePurchasedItemsDto(transaction, product.getId(),
            product.getProductName(), product.getBrand().getBrandName(),
            product.getCategory().getCategoryName(), product.getPrice(),
            amount, false, "");
        purchasedItemsRepository.save(new PurchasedItems(dto));
      });

    });
  }

  private Integer selectAmount(BigDecimal price) {
    if (price.compareTo(new BigDecimal("3")) < 0) {
      return (int) Math.ceil(Math.random() * 100);
    } else if (price.compareTo(new BigDecimal("30")) < 0) {
      return (int) Math.ceil(Math.random() * 10);
    } else if (price.compareTo(new BigDecimal("60")) < 0) {
      return (int) Math.ceil(Math.random() * 5);
    }
    return (int) Math.ceil(Math.random() * 2);
  }
}
