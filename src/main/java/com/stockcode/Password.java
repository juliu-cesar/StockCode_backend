package com.stockcode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Password {
  public static String getPassword() {
    String filePath = "C:\\Users\\Cesar\\Desktop\\Curso_Dev\\Alura\\passwords.csv";
    String searchWord = "mysql";
    String password = "";

    try{

      BufferedReader buffer = new BufferedReader(new FileReader(filePath));
      String line;

      while ((line = buffer.readLine()) != null) {
        if (line.contains(searchWord)) {
          password = line.substring(line.indexOf(",") + 1, line.length());
        }
      }
    } catch (FileNotFoundException e){
      System.out.println("Arquivo [password.csv] não encontrado.\n");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("Erro na leitura do arquivo [password.csv].");
      e.printStackTrace();
    }
    return password;
  }
}
