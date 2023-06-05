package com.stockcode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import com.google.gson.Gson;
import com.stockcode.product.ProductService;

public class StockCodeApplication {
  public static void main(String[] args) {
    Gson gson = new Gson();
    
    // ProductTeste teste = new ProductTeste();
    // teste.setId(271);
    // teste.setName("Classe teste GSON.");
    // teste.setPrice(new BigDecimal("10.65"));

    // String json = gson.toJson(teste);
    // System.out.println("resultado: "+json);

    // String deserialize = "{\"id\":271,\"name\":\"\",\"price\":10.65684}";

    // ProductTeste product = gson.fromJson(deserialize, ProductTeste.class);
    // System.out.println(product.toString());

    // BigDecimal price = new BigDecimal("28");
    // System.out.println(price.setScale(2, RoundingMode.DOWN));

    ProductService prS = new ProductService();
    prS.changeBrand(4678513, 6847514);
  }
}
