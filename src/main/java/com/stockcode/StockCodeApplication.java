package com.stockcode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class StockCodeApplication {
  public static void main(String[] args) {
    DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
    dfs.setDecimalSeparator('.');
    DecimalFormat df = new DecimalFormat("#0.00", dfs);

    BigDecimal price = new BigDecimal("3.6587464");
    price = price.setScale(2, RoundingMode.DOWN);
    System.out.println(df.format(price));

  }
}
