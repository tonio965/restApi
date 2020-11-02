package com.exampleapi.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.exampleapi.model.ConnectionWrapper;

public interface ConnectionPool {
  
  ConnectionWrapper getConnection()  throws SQLException, InterruptedException, ClassNotFoundException;
  boolean releaseConnection(ConnectionWrapper connection);
  void invalidConnection(Connection connection);
  public ConnectionWrapper createConnection() throws SQLException, ClassNotFoundException;
}
