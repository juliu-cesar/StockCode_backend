package com.stockcode.product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO {
  private Connection conn;

  public ProductDAO(Connection connection) {
    this.conn = connection;
  }

  public void createProduct(FieldsProduct fieldsData) {
    Product product = new Product(fieldsData.id(), fieldsData.brand_id(), fieldsData.category_id(), fieldsData.name(),
        fieldsData.price(), fieldsData.product_description());
    PreparedStatement ps;

    String sql = "INSERT INTO product(id,brand_id,category_id,name,price,product_description) VALUES(?,?,?,?,?,?)";
    try {
      ps = conn.prepareStatement(sql);

      ps.setInt(1, product.getId());
      ps.setInt(2, product.getBrand_id());
      ps.setInt(3, product.getCategory_id());
      ps.setString(4, product.getName());
      ps.setBigDecimal(5, product.getPrice());
      ps.setString(6, product.getProduct_description());

      ps.execute();
      ps.close();
      conn.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public ArrayList<Product> listProducts() {
    String sql = "SELECT * FROM product";
    return getList(sql);
  }
  public ArrayList<Product> searchByName(String search) {    
    String sql = "SELECT * FROM product WHERE name LIKE "+search;
    return getList(sql);
  }
  public ArrayList<Product> searchByBrand(String search) {    
    String sql = "SELECT * FROM product WHERE brand LIKE "+search;
    return getList(sql);
  }

  // public String updateName(String name){
  //   if(name.trim() == ""){
  //      return "[ERRO] nome invalido. Não foi possível alterar o nome.";      
  //   }
  //   String sql = "UPDATE product SET name = "+name;
  // }


  private ArrayList<Product> getList(String sql){
    ArrayList<Product> products = new ArrayList<>();
    PreparedStatement ps;
    ResultSet rs;
    try {
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      while (rs.next()) {
        products.add(getProduct(rs));
      }
      ps.close();
      rs.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return products;
  }

  private Product getProduct(ResultSet rs) {
    Integer id;
    Integer brand_id;
    Integer category_id;
    String name;
    BigDecimal price;
    String product_description;
    try {
      id = rs.getInt(1);
      brand_id = rs.getInt(2);
      category_id = rs.getInt(3);
      name = rs.getString(4);
      price = rs.getBigDecimal(5);
      product_description = rs.getString(6);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return new Product(id, brand_id, category_id, name, price, product_description);
  }
}
