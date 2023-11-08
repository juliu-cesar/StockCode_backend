package com.stockapi.StockCode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockapi.StockCode.utils.fillAllTables.FillAllTablesService;

@RestController
@RequestMapping("fill-all-tables")
public class FillAllTablesController {

  @Autowired
  private FillAllTablesService fillAllTablesService;

  @PostMapping
  @Transactional
  public String fillTables(){
    var message = fillAllTablesService.fillAllTables();
    return message;
  }
  
  @DeleteMapping 
  @Transactional
  public String emptyTheTables(){
    var message = fillAllTablesService.emptyAllTheTables();
    return message;
  }
}

