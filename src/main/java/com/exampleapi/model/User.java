package com.exampleapi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

  public boolean login(Connection con, String name, String password) throws SQLException {
    PreparedStatement stmt = con.prepareStatement("select GROUP_ID from CLUSTER where NAME = ? and PASSWORD_HASH = sha2(?, 512)");
    stmt.setString(1, name);
    stmt.setString(2, password);
    
    ResultSet results = stmt.executeQuery();
    if (results.next()) {
      return true;
    } else {
      return false;
    }
  }
}

