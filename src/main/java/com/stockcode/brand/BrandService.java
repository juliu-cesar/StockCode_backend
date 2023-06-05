package com.stockcode.brand;

import java.sql.Connection;
import java.util.ArrayList;

import com.stockcode.ConnectionDB;

public class BrandService {
  private ConnectionDB connectionDB;

  public BrandService() {
    this.connectionDB = new ConnectionDB();
  }

  public ArrayList<Brand> searchAllBrands() {
    Connection connection = connectionDB.getConnection();
    return new BrandDAO(connection).brandList();
  }

  public ArrayList<Brand> searchBrandById(Integer id) {
    validateId(id);
    Connection connection = connectionDB.getConnection();
    return new BrandDAO(connection).selectBrandById(id);
  }

  private void validateId(Integer id) {
    Boolean hasEightDigits = id > 9999999 && id < 100000000;
    if (!hasEightDigits) {
      throw new RuntimeException("Marca não existe ou identificador da marca incorreto.");
    }
  }
}
