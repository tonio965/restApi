package com.exampleapi.jdbc;


import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exampleapi.model.ConnectionWrapper;

@Component
public class Database{

  private DatabaseSettings settings;
  public RAMSSettings rams;
  private ConnectionPoolImpl connectionPool;
  

  @Autowired
  public Database(DatabaseSettings settings, RAMSSettings rams) throws SQLException {
    System.out.println("Database(): " + settings);
    this.settings = settings;
    this.rams = rams;
    System.out.println("RAMS(): " + rams);
    connectionPool = new ConnectionPoolImpl(settings);
     
  }
  
  public ConnectionWrapper getConnection() throws SQLException, InterruptedException, ClassNotFoundException {
    ConnectionWrapper con = connectionPool.getConnection();
    return con;
  }
  
  public Boolean releaseConnection(ConnectionWrapper c) {
    connectionPool.releaseConnection(c);
    return true;
  }
//  @Scheduled(fixedRate = 5000)
//  public void removeUnusedConnections() {
//           System.out.println("test");
//  }

  public void invalidConnection(ConnectionWrapper cw) {
    connectionPool.releaseConnection(cw);
    // TODO Auto-generated method stub
    
  }
}


