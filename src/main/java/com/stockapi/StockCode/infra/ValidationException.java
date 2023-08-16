package com.stockapi.StockCode.infra;

public class ValidationException extends RuntimeException {

  public ValidationException(String message) {
    super(message);
  }

}
