package com.exampleapi.jdbc.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.exampleapi.model.ReloadChannel;

@Component
public class ReloadChannelDAO {
  
  private static final Logger LOG = LogManager.getLogger(ReloadChannelDAO.class.getName());

  
  public ReloadChannel readFromName(String name, String subChannel, Connection connection) throws SQLException {
    PreparedStatement ps=null;
    ResultSet result = null;
    ReloadChannel rc=new ReloadChannel();
    try {
      ps = connection.prepareStatement("SELECT * FROM RELOAD_CHANNEL WHERE NAME=? AND SUB_CHANNEL=?"); //zmeinic na name i subchannel
      ps.setString(1,name);
      ps.setString(2, subChannel);
      result=ps.executeQuery();
      LOG.trace("ReloadChannelDAO: reading from db");
      while(result.next()) {
        rc.setID(result.getInt(1));
        rc.setNAME(result.getString(2));
        rc.setSUB_CHANNEL(result.getString(3));
        rc.setDESCRIPTION(result.getString(4));
        rc.setCHANNEL_TYPE(result.getString(5));
        rc.setMAX_AMOUNT(result.getInt(6));
        rc.setMIN_AMOUNT(result.getInt(7));
        rc.setRESERVATION_TIME(result.getInt(8));        
      }
    }
    catch(RuntimeException e) {
      
    }
    
    finally {
      if(ps!=null)
        try {
          ps.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      if(result!=null)
        try {
          result.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
    }
    
    return rc;
  }
  
}