package com.exampleapi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.exampleapi.jdbc.Database;
import com.exampleapi.jdbc.DatabaseSettings;


@SpringBootApplication
@EnableScheduling
@EnableAsync
public class ReloadApp {
  public static final Logger LOG = LogManager.getLogger(ReloadApp.class);
  public static Connection conn = null;
	public ReloadApp(){
	  LOG.info("ReloadApp started");
	}
	

	public static void main(String[] args) {  

    try {
//      conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/reload_db", "rld", "rld");
      SpringApplication.run(ReloadApp.class, args);
    } catch(Exception e) {
      System.out.println("ReloadApp run failed: "+e.getMessage());
      System.exit(-1);
    }
  }
	
}
