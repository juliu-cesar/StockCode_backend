package com.stockcode.product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.util.ArrayList;

import com.stockcode.ConnectionDB;

public class ProductService {
  private ConnectionDB connectionDB;

  public ProductService() {
    this.connectionDB = new ConnectionDB();
  }

  public void addNewProductToList(FieldsProduct fieldsProduct) {
    validateId(fieldsProduct.id());
    validateBrandId(fieldsProduct.brand_id());
    validateCategoryID(fieldsProduct.category_id());
    validateName(fieldsProduct.name());
    Connection connection = connectionDB.getConnection();
    new ProductDAO(connection).insertProduct(fieldsProduct, normalizePrice(fieldsProduct.price()));
  }

  public ArrayList<Product> searchAllProduct() {
    Connection connection = connectionDB.getConnection();
    return new ProductDAO(connection).productList();
  }
  
  public ArrayList<Product> searchProductByBrand(Integer brand_id) {
    validateId(brand_id);
    Connection connection = connectionDB.getConnection();
    return new ProductDAO(connection).selectByBrand(brand_id);
  }

  public ArrayList<Product> searchProductByCategory(Integer category_id) {
    validateCategoryID(category_id);
    Connection connection = connectionDB.getConnection();
    return new ProductDAO(connection).selectByCategory(category_id);
  }

  public void changeProductBrand(Integer brand_id, Integer id) {
    validateId(id);
    validateBrandId(brand_id);
    Connection connection = connectionDB.getConnection();
    new ProductDAO(connection).updateBrand(brand_id, id);
  }

  public void changeProductCategory(Integer category_id, Integer id) {
    validateId(id);
    validateCategoryID(category_id);
    Connection connection = connectionDB.getConnection();
    new ProductDAO(connection).updateBrand(category_id, id);
  }

  public void changeProductName(String name, Integer id) {
    validateId(id);
    validateName(name);
    Connection connection = connectionDB.getConnection();
    new ProductDAO(connection).updateName(name, id);
  }

  public void changeProductPrice(BigDecimal price, Integer id) {
    validateId(id);
    Connection connection = connectionDB.getConnection();
    new ProductDAO(connection).updatePrice(normalizePrice(price), id);
  }

  public void removeProductFromList(Integer id) {
    validateId(id);
    Connection connection = connectionDB.getConnection();
    new ProductDAO(connection).deleteProduct(id);
  }

  private void validateId(Integer id) {
    Boolean hasNineDigits = id > 99999999 && id < 1000000000;
    if (!hasNineDigits) {
      throw new RuntimeException("ID do produto incorreto.");
    }
  }

  private void validateBrandId(Integer id) {
    Boolean hasEightDigits = id > 9999999 && id < 100000000;
    // TODO verify if brand_id and category_id exist.
    if (!hasEightDigits) {
      throw new RuntimeException("Marca não existe ou identificador da marca incorreto.");
    }
  }

  private void validateCategoryID(Integer id) {
    Boolean hasEightDigits = id > 9999999 && id < 100000000;
    // TODO verify if brand_id and category_id exist.
    if (!hasEightDigits) {
      throw new RuntimeException("Categoria não existe ou identificador da categoria incorreto.");
    }
  }

  private void validateName(String name) {
    Boolean nameIsEmpty = name.trim() != "";
    if (!nameIsEmpty) {
      throw new RuntimeException("Nome invalido para o produto.");
    }
  }

  private BigDecimal normalizePrice(BigDecimal price) {
    return price.setScale(2, RoundingMode.DOWN);
  }
}
