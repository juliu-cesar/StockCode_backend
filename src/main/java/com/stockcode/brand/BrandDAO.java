package com.stockcode.brand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BrandDAO {
  private Connection conn;

  public BrandDAO(Connection connection) {
    this.conn = connection;
  }

  public ArrayList<Brand> brandList() {
    String sql = "SELECT * FROM brand";
    return getList(sql);
  }

  public ArrayList<Brand> selectBrandById(Integer id) {
    String sql = "SELECT * FROM brand WHERE id = " + id;
    return getList(sql);
  }

  private ArrayList<Brand> getList(String sql) {
    ArrayList<Brand> brands = new ArrayList<>();
    PreparedStatement ps;
    ResultSet rs;
    try {
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      while (rs.next()) {
        brands.add(getBrand(rs));
      }
      ps.close();
      rs.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return brands;
  }

  private Brand getBrand(ResultSet rs) {
    Integer id;
    String brand_name;
    try {
      id = rs.getInt(1);
      brand_name = rs.getString(2);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return new Brand(id, brand_name);
  }
}
