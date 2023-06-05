package com.stockcode.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAO {
  private Connection conn;

  public CategoryDAO(Connection connection) {
    this.conn = connection;
  }

  public ArrayList<Category> categoryList() {
    String sql = "SELECT * FROM category";
    return getList(sql);
  }

  public ArrayList<Category> selectCategoryById(Integer id) {
    String sql = "SELECT * FROM category WHERE id = " + id;
    return getList(sql);
  }

  private ArrayList<Category> getList(String sql) {
    ArrayList<Category> categories = new ArrayList<>();
    PreparedStatement ps;
    ResultSet rs;
    try {
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      while (rs.next()) {
        categories.add(getCategory(rs));
      }
      ps.close();
      rs.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return categories;
  }

  private Category getCategory(ResultSet rs) {
    Integer id;
    String category;
    try {
      id = rs.getInt(1);
      category = rs.getString(2);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return new Category(id, category);
  }
}
