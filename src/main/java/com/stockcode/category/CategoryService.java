package com.stockcode.category;

import java.sql.Connection;
import java.util.ArrayList;

import com.stockcode.ConnectionDB;

public class CategoryService {
  private ConnectionDB connectionDB;

  public CategoryService() {
    this.connectionDB = new ConnectionDB();
  }

  public ArrayList<Category> searchAllCategories() {
    Connection connection = connectionDB.getConnection();
    return new CategoryDAO(connection).categoryList();
  }

  public ArrayList<Category> searchBrandById(Integer id) {
    validateId(id);
    Connection connection = connectionDB.getConnection();
    return new CategoryDAO(connection).selectCategoryById(id);
  }

  private void validateId(Integer id) {
    Boolean hasEightDigits = id > 9999999 && id < 100000000;
    if (!hasEightDigits) {
      throw new RuntimeException("Categoria não existe ou identificador da categoria incorreto.");
    }
  }
}
