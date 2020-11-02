package com.exampleapi.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.exampleapi.model.ConnectionWrapper;


@EnableScheduling
@Component
public class ConnectionPoolImpl2 implements ConnectionPool{
  
  private static final Logger LOG = LogManager.getLogger(ConnectionPoolImpl2.class.getName());
  private static List<ConnectionWrapper> idleConnections;
  private static int allConnectionsCounter;
  private DatabaseSettings ds;

  public ConnectionPoolImpl2(DatabaseSettings ds) throws SQLException {
    idleConnections= new ArrayList<>();
    allConnectionsCounter= 0;    
    this.ds=ds;
  }
  @Override
  public ConnectionWrapper createConnection() throws SQLException, ClassNotFoundException {
    Class.forName("com.mysql.cj.jdbc.Driver"); 
    ConnectionWrapper connection = new ConnectionWrapper(
          DriverManager.getConnection(ds.getDbUrl(), ds.getDbUser(), 
              ds.getDbPassword()),System.currentTimeMillis());
    
    return connection;
  }
  
  @Override
  public synchronized ConnectionWrapper getConnection() throws SQLException, InterruptedException, ClassNotFoundException {
    //check if there is something in connection pool
    if(idleConnections.size()==0) {
      //if no, check if number of current connections < max, if no add new to pool
      if(allConnectionsCounter<ds.getMaxPoolSize()) {
        ConnectionWrapper connection = createConnection();
        allConnectionsCounter++;
        LOG.info("ConnectionPool.getConnection() new Connection. DBPool[max:{},opened:{},idle:{}]"
            ,ds.getMaxPoolSize(),allConnectionsCounter,idleConnections.size());
        return connection;
      }
      //if all connections are current 
      else {
        //spin the loop n times and check if something freed up to use if so - use it and return or throw exception
        for(int i=0;i<ds.getMaxRetryNr();i++) {
          if(i == ds.getMaxRetryNr()-1) {
            LOG.warn("ConnectionPool.getConnection() Could not connect to database. SQLException: {}");
          }
            
//          jezeli connection pool jest puste a uzywanych polaczen jest mniej niz max - stworzyc nowe, 
//          niby nie powinno byc takiej sytuacji za czesto ale zdarzalo sie pewnie przez zle wartosci w .poperties
          if(allConnectionsCounter<ds.getMaxPoolSize() && idleConnections.isEmpty()) { 
            ConnectionWrapper connection = createConnection();
            allConnectionsCounter++;
            LOG.info("ConnectionPool.getConnection() idle connection taken. DBPool[max:{},opened:{},idle:{}]"
                ,ds.getMaxPoolSize(),allConnectionsCounter++,idleConnections.size());
            return connection;
          }
//        jezeli jest cos w pool oraz aktywcnych polaczen jest mniej niz max
          if(allConnectionsCounter<ds.getMaxPoolSize() && !idleConnections.isEmpty()) {
            ConnectionWrapper connection = idleConnections.remove(idleConnections.size() - 1);
            connection.setLastUsedMilis(System.currentTimeMillis());
            LOG.info("ConnectionPool.getConnection() idle connection taken. DBPool[max:{},opened:{},idle:{}]"
                ,ds.getMaxPoolSize(),allConnectionsCounter,idleConnections.size());

            return connection;
          }
          Thread.sleep(ds.getMaxRetryInterval());
            
        }
        
      }
    }
    //if there is something free in the pool - use it and return
    ConnectionWrapper connection = idleConnections.remove(idleConnections.size() - 1);
    connection.setLastUsedMilis(System.currentTimeMillis()); 
    return connection;
  }
  

  @Override
  public synchronized boolean releaseConnection(ConnectionWrapper connection) {
    idleConnections.add(connection);
    connection.setLastUsedMilis(System.currentTimeMillis()); 
    LOG.info("ConnectionPool::releaseConnection() DBPool[max:{},opened:{},idle:{}]"
        ,ds.getMaxPoolSize(),allConnectionsCounter,idleConnections.size());
    return true;
  }

  public void invalidConnection(ConnectionWrapper connection) {
    if (connection != null) {
      allConnectionsCounter--;//close
      LOG.info("ConnectionPool::invalidConnection() DBPool[max:{},opened:{},idle:{}]"
          ,ds.getMaxPoolSize(),allConnectionsCounter,idleConnections.size());
    }
  }

  public synchronized void removeConn() {
//    System.out.println("removeunusedConn(): poolsize: "+connectionPool.size()+", currentConn: "+currentConnections.size());
    for(int i=idleConnections.size()-1; i>=0; i--){
      ConnectionWrapper cw = idleConnections.get(i);
      if(cw.getLastUsedMilis()+ds.getInactiveCloseDelay()<System.currentTimeMillis()) {
        LOG.info("ConnectionPool::close() DBPool[max:{},opened:{},idle:{}]"
            ,ds.getMaxPoolSize(),allConnectionsCounter,idleConnections.size());
        idleConnections.remove(i);

      }
      else {
//        System.out.println("nothing to release");
      }

    }
  }

  @Scheduled(fixedRate = 20)
  public void removeUnusedConnections() {
    removeConn();
  }
  @Override
  public void invalidConnection(Connection connection) {
    // TODO Auto-generated method stub
    
  }

  
  

}
