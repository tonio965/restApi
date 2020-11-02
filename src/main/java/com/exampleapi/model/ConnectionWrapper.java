package com.exampleapi.model;

import java.sql.Connection;

//this class is just to wrap Connection with a timestamp
public class ConnectionWrapper {
  private Connection connection;
  private long lastUsedMilis;
  
  public ConnectionWrapper() {

  }
  
  public ConnectionWrapper(Connection connection, long lastUsedMilis) {
    super();
    this.connection = connection;
    this.lastUsedMilis = lastUsedMilis;
  }
  public Connection getConnection() {
    return connection;
  }
  public void setC(Connection connection) {
    this.connection = connection;
  }
  public long getLastUsedMilis() {
    return lastUsedMilis;
  }
  public void setLastUsedMilis(long lastUsedMilis) {
    this.lastUsedMilis = lastUsedMilis;
  }
  
  
  

}
