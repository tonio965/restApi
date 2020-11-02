package com.exampleapi.jdbc;

import java.sql.SQLException;

import com.exampleapi.model.User;

public class DBTest {

  private Database db;
  
  public DBTest(Database db) {
    this.db = db;
  }
  
  public void execute() {
    System.out.println("DBTest.execute() start");
    boolean loginResult = false;
      String username = "rld";
      String password = "rld";
      
      User user = new User();
      loginResult = false;

    
    
    if (loginResult) {
      System.out.println("Login successful");
    } else {
      System.out.println("Login failed");
    }
   
  }
}

