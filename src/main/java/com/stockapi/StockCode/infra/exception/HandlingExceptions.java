package com.stockapi.StockCode.infra.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
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

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<String> handleValidationException(ValidationException ex) {
    // ex.printStackTrace();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<String> handleEntityNotFoundException(ValidationException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  private record ValidationErrorDto(String field, String message) {
    public ValidationErrorDto(FieldError error) {
      this(error.getField(), error.getDefaultMessage());
    }
  }
}
