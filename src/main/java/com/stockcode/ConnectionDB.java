package com.stockcode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
  public Connection getConnection() {
    try {
      String url = "jdbc:mysql://localhost:3306/stockcode?user=root&password="
        + Password.getPassword();
      return DriverManager.getConnection(url);
    } catch (SQLException err) {
      throw new RuntimeException(err);
    }
  }
}
