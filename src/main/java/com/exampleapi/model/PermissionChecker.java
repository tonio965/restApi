package com.exampleapi.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.exampleapi.ReloadApp;

public class PermissionChecker {
  
  public Integer checkPermission(Connection connection,String name, String pw, String module){
    PreparedStatement ps=null;
    ResultSet rs=null;
    try {
      ps = connection.prepareStatement("SELECT PERMISSION FROM CLTACCESS cltaccess "  //rozdzielic na login i modul oddzielnie
          + "JOIN CLTGROUP cltgroup ON cltaccess.GROUP_ID = cltgroup.ID " + "JOIN CLUSTER user ON cltgroup.ID = user.GROUP_ID "
          + "JOIN CLTMODULE cltmodule ON cltmodule.ID = cltaccess.MODULE_ID " + "WHERE user.NAME = ? AND user.PASSWORD_HASH = sha2(?, 512) AND cltmodule.NAME = ?");
      ps.setString(1,name);
      ps.setString(2, pw);
      ps.setNString(3, module);
      rs=ps.executeQuery();
      int b=7; // 7 is just to know if 0 or 1 came from db
      while(rs.next()){   
        b = rs.getInt(1);
      }  
      return b;

    } catch (SQLException e) {
        throw new RuntimeException("something went wrong during db connection in permissionChecker");
  
    }
    finally {
      if(ps!=null)
        try {
          ps.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      if(rs!=null)
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
    }
  }
  
      
}
  

