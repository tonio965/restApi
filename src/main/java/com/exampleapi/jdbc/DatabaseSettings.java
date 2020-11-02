package com.exampleapi.jdbc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSettings {
  
  @Value("${db.url}")
  private String dbUrl;
  
  @Value("${db.username}")
  private String dbUser;
  
  @Value("${db.password}")
  private String dbPassword;
  
  @Value("${db.max_pool_size}")
  private int maxPoolSize;
  
  @Value("${db.max_retry_nr}")
  private int maxRetryNr;
  
  @Value("${db.retry_interval}")
  private int maxRetryInterval;
  
  @Value("${db.inactive_close_delay}")
  private int inactiveCloseDelay;
  
  
  

  public String getDbUrl() {
    return dbUrl;
  }

  public void setDbUrl(String dbUrl) {
    this.dbUrl = dbUrl;
  }


  public String getDbUser() {
    return dbUser;
  }


  public void setDbUser(String dbUser) {
    this.dbUser = dbUser;
  }


  public String getDbPassword() {
    return dbPassword;
  }


  public void setDbPassword(String dbPassword) {
    this.dbPassword = dbPassword;
  }


  public int getMaxPoolSize() {
    return maxPoolSize;
  }


  public void setMaxPoolSize(int maxPoolSize) {
    this.maxPoolSize = maxPoolSize;
  }


  public int getMaxRetryNr() {
    return maxRetryNr;
  }


  public void setMaxRetryNr(int maxRetryNr) {
    this.maxRetryNr = maxRetryNr;
  }


  public int getMaxRetryInterval() {
    return maxRetryInterval;
  }


  public void setMaxRetryInterval(int maxRetryInterval) {
    this.maxRetryInterval = maxRetryInterval;
  }


  public int getInactiveCloseDelay() {
    return inactiveCloseDelay;
  }


  public void setInactiveCloseDelay(int inactiveCloseDelay) {
    this.inactiveCloseDelay = inactiveCloseDelay;
  }

  @Override
  public String toString() {
    return "DatabaseSettings [dbUrl=" + dbUrl + ", dbUser=" + dbUser + ", dbPassword=" + dbPassword + ", maxPoolSize="
        + maxPoolSize + ", maxRetryNr=" + maxRetryNr + ", maxRetryInterval=" + maxRetryInterval
        + ", inactiveCloseDelay=" + inactiveCloseDelay + "]";
  }





}

