package com.exampleapi.jdbc.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Controller;

import com.exampleapi.ReloadApp;
import com.exampleapi.model.Cltgroup;


@Controller
public class CltgroupDAOImpl implements CltgroupDAO{
  

  @Override
  public Cltgroup findById(int id, Connection conn) {

    Cltgroup c = null;
    try {
      PreparedStatement ps = conn.prepareStatement("select * from CLTGROUP where ID = ?");
      ps.setInt(1,id);
      ResultSet rs=ps.executeQuery();  
      while(rs.next()){  
        c = new Cltgroup(rs.getInt(1), rs.getString(2), rs.getString(3));
//        System.out.println(c.toString());  
      }  
      ps.close();
      conn.close();

    } catch (SQLException e) {
        throw new RuntimeException(e);
  
    }
    return c;
}

  @Override
  public int insert(Cltgroup cltgroup) {
    try {
      PreparedStatement ps = ReloadApp.conn.prepareStatement("insert into CLTGROUP (ID, NAME, DESCRIPTION) values(?,?,?)");
      ps.setInt(1, cltgroup.getId());//
      ps.setString(2, cltgroup.getName());
      ps.setString(3, cltgroup.getDescription());
      ps.executeUpdate();
      ps.close();

  } catch (SQLException e) {
      throw new RuntimeException(e);

  }
    return 1;
  }


  @Override
  public int deleteById(int id) {
    Connection conn = null;
    try {
      PreparedStatement ps = ReloadApp.conn.prepareStatement("delete from CLTGROUP where ID = ?");
      ps.setInt(1, 1);
      ps.executeUpdate();
      ps.close();

  } catch (SQLException e) {
      throw new RuntimeException(e);

  }
    return 1;

  }

}
