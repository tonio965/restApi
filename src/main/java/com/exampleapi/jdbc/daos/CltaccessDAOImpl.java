package com.exampleapi.jdbc.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Repository;

import com.exampleapi.ReloadApp;
import com.exampleapi.model.Cltaccess;

@Repository
public class CltaccessDAOImpl implements CltaccessDAO{

  @Override
  public int insert(Cltaccess cltaccess) {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/reload_db", "rld", "rld");
      PreparedStatement ps = conn.prepareStatement("insert into CLTACCESS (MODULE_ID, GROUP_ID, PERMISSION) values(?,?,?)");
      ps.setInt(1, cltaccess.getModule_id());//
      ps.setInt(2, cltaccess.getGroup_id());
      ps.setInt(3, cltaccess.getPermission());
      ps.executeUpdate();
      ps.close();
      conn.close();
      return 1;

  } catch (SQLException e) {
      throw new RuntimeException(e);
      

    }
  }

  @Override
  public Cltaccess findByModuleId(int module, int group) {
    Cltaccess c = null;
    try {
      PreparedStatement ps = ReloadApp.conn.prepareStatement("select * from CLTACCESS where MODULE_ID = ? and GROUP_ID = ?");
      ps.setInt(1,module);
      ps.setInt(2, group);
      ResultSet rs=ps.executeQuery();  
      while(rs.next()){  
        c = new Cltaccess(rs.getInt(1), rs.getInt(2), rs.getInt(3));
        System.out.println(c.toString());  
      }  
      ps.close();


  } catch (SQLException e) {
      throw new RuntimeException(e);

  }
    return c;
  }

  @Override
  public int deleteById(int module, int group) {
    try {
      PreparedStatement ps = ReloadApp.conn.prepareStatement("delete from CLTACCESS where MODULE_ID = ? and GROUP_ID = ?");
      ps.setInt(1, module);
      ps.setInt(2, group);
      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
        throw new RuntimeException(e);

    }
    return 1;

  }
 }
  
