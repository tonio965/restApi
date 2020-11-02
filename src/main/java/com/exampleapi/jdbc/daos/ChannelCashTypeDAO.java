package com.exampleapi.jdbc.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.exampleapi.model.ChannelCashType;
import com.exampleapi.model.ReloadChannel;

@Component
public class ChannelCashTypeDAO {
  
  private static final Logger LOG = LogManager.getLogger(ReloadChannelDAO.class.getName());
  
  public ChannelCashType getChannelCashType(ReloadChannel rc, Connection connection) throws SQLException {
    PreparedStatement ps=null;
    ResultSet result = null;
    ChannelCashType cashType =new ChannelCashType();
    try {
      ps = connection.prepareStatement("SELECT * FROM CHANNEL_CASH_TYPE WHERE RELOAD_CHANNEL_ID=?"); //zmienic na nazwy klumn
      ps.setInt(1,rc.getID());
      result=ps.executeQuery();
      LOG.trace("ChannelCashTypeDAO: reading from db");
      while(result.next()) {
        cashType.setRELOAD_CHANNEL_ID(result.getInt(1));
        cashType.setCASH_TYPE(result.getString(2));
        cashType.setCASH_NAME(result.getString(3));
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
    
    return cashType;
  }

}
