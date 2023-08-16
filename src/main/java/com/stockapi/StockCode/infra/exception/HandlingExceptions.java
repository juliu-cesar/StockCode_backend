package com.stockapi.StockCode.infra.exception;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlingExceptions {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<HandlingExceptions.ValidationErrorDto>> trataErro400(MethodArgumentNotValidException ex) {
    var erros = ex.getFieldErrors();
    return ResponseEntity.badRequest().body(erros.stream().map(ValidationErrorDto::new).toList());
  }

  private record ValidationErrorDto(String field, String message) {
    public ValidationErrorDto(FieldError error) {
      this(error.getField(), error.getDefaultMessage());
    }
  }
}
