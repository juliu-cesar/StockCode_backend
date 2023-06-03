package com.stockcode.product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import com.stockcode.ConnectionDB;

public class ProductService {
  private ConnectionDB connectionDB;

  public ProductService(){
    this.connectionDB = new ConnectionDB();
  }

  public void addNewProductToList(FieldsProduct fieldsProduct){
    if(!validateId(fieldsProduct.id())){
      throw new RuntimeException("ID do produto incorreto.");
    }
    if(!validateBrandId_categoryID(fieldsProduct.brand_id())){
      throw new RuntimeException("Marca não existe ou identificador da marca incorreto.");
    }
    if(!validateBrandId_categoryID(fieldsProduct.category_id())){
      throw new RuntimeException("Categoria não existe ou identificador da categoria incorreto.");
    }
    if(!validateName(fieldsProduct.name())){
      throw new RuntimeException("Nome invalido para o produto.");
    }
    Connection connection = connectionDB.getConnection();
    new ProductDAO(connection).insertProduct(fieldsProduct);
  }


  private Boolean validateId(Integer id){
    Boolean hasNineDigits = id > 99999999 && id < 1000000000;
    return hasNineDigits;
  }
  private Boolean validateBrandId_categoryID(Integer id){
    Boolean hasEightDigits = id > 9999999 && id < 100000000;

    // TODO verify if brand_id and category_id exist.
    
    return hasEightDigits;
  }
  private Boolean validateName(String name){
    Boolean nameIsEmpty = name.trim() != "";
    return nameIsEmpty;
  }
  private BigDecimal normalizePrice(String price_string){
    DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
    dfs.setDecimalSeparator('.');
    DecimalFormat df = new DecimalFormat("#0.00", dfs);

    BigDecimal price = new BigDecimal(price_string);
    price = price.setScale(2, RoundingMode.DOWN);
    return new BigDecimal(df.format(price));
  }
}
