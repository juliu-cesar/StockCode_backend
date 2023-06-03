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

  public void insertProduct(FieldsProduct fieldsData) {
    PreparedStatement ps;
    String sql = "INSERT INTO product(id,brand_id,category_id,name,price,product_description) VALUES(?,?,?,?,?,?)";
    try {
      ps = conn.prepareStatement(sql);

      ps.setInt(1, fieldsData.id());
      ps.setInt(2, fieldsData.brand_id());
      ps.setInt(3, fieldsData.category_id());
      ps.setString(4, fieldsData.name());
      ps.setBigDecimal(5, fieldsData.price());
      ps.setString(6, fieldsData.product_description());

      ps.execute();
      ps.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public ArrayList<Product> productsList() {
    String sql = "SELECT * FROM product";
    return getList(sql);
  }
  public ArrayList<Product> searchByBrand(Integer brand_id) {
    String sql = "SELECT * FROM product WHERE brand_id = " + brand_id;
    return getList(sql);
  }
  public ArrayList<Product> searchByCategory(Integer category_id) {
    String sql = "SELECT * FROM product WHERE category_id = " + category_id;
    return getList(sql);
  }

  // -------------------- \\ --------------------
  // #TODO implement the search by name

  // public ArrayList<Product> searchByName(String search) {
    //   String sql = "SELECT * FROM product WHERE name LIKE " + search;
    //   return getList(sql);
    // }
  // -------------------- \\ --------------------

  public void updateBrand(Integer brand_id, Integer id) {
    PreparedStatement ps;
    String sql = "UPDATE product SET brand_id = ? WHERE id = ?";
    try {
      ps = conn.prepareStatement(sql);
      ps.setInt(1, brand_id);
      ps.setInt(1, id);

      ps.execute();
      ps.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  public void updateCategory(Integer category_id, Integer id) {
    PreparedStatement ps;
    String sql = "UPDATE product SET category_id = ? WHERE id = ?";
    try {
      ps = conn.prepareStatement(sql);
      ps.setInt(1, category_id);
      ps.setInt(1, id);

      ps.execute();
      ps.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  public void updateName(String name, Integer id) {
    PreparedStatement ps;
    String sql = "UPDATE product SET name = ? WHERE id = ?";
    try {
      ps = conn.prepareStatement(sql);
      ps.setString(1, name);
      ps.setInt(1, id);

      ps.execute();
      ps.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  public void updatePrice(BigDecimal price, Integer id) {
    PreparedStatement ps;
    String sql = "UPDATE product SET price = ? WHERE id = ?";
    try {
      ps = conn.prepareStatement(sql);
      ps.setBigDecimal(1, price);
      ps.setInt(1, id);

      ps.execute();
      ps.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void deleteProduct(Integer id) {
    PreparedStatement ps;
    String sql = "DELETE FROM product WHERE id = " + id;
    try {
      ps = conn.prepareStatement(sql);
      ps.execute();
      ps.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private ArrayList<Product> getList(String sql) {
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
