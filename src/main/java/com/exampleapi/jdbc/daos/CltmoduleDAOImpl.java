package com.exampleapi.jdbc.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.exampleapi.ReloadApp;
import com.exampleapi.model.Cltmodule;

public class CltmoduleDAOImpl implements CltmoduleDAO {

  @Override
  public int insert(Cltmodule cltmodule) {
    try {
      PreparedStatement ps = ReloadApp.conn.prepareStatement("insert into CLTMODULE (ID, NAME, DESCRIPTION) values(?,?,?)");
      ps.setInt(1, cltmodule.getId());//
      ps.setString(2, cltmodule.getName());
      ps.setString(3, cltmodule.getDescription());
      ps.executeUpdate();
      ps.close();

    } catch (SQLException e) {
        throw new RuntimeException(e);
  
    }
    return 1;
      
  }

  @Override
  public Cltmodule findById(int id) {
    Cltmodule c = null;
    try {
      PreparedStatement ps = ReloadApp.conn.prepareStatement("select * from CLTMODULE where ID = ?");
      ps.setInt(1,id);
      ResultSet rs=ps.executeQuery();  
      while(rs.next()){  
        c = new Cltmodule(rs.getInt(1), rs.getString(2), rs.getString(3));
        System.out.println(c.toString());  
      }  
      ps.close();

    } catch (SQLException e) {
        throw new RuntimeException(e);
  
    }
    return c;
  }

  @Override
  public int deleteById(int id) {
    try {
      PreparedStatement ps = ReloadApp.conn.prepareStatement("delete from CLTMODULE where ID = ?");
      ps.setInt(1, 1);
      ps.executeUpdate();
      ps.close();

  } catch (SQLException e) {
      throw new RuntimeException(e);

  }
    return 1;
  }

}
