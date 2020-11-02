package com.exampleapi.jdbc.daos;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.exampleapi.ReloadApp;
import com.exampleapi.model.Cluster;
import com.exampleapi.path.PathTest3;

public class ClusterDAOImpl implements ClusterDAO{
  
  private static final Logger LOG = LogManager.getLogger(ClusterDAOImpl.class.getName());
  
  public Cluster findById(String name, String pw) {
    Cluster c = null;
    try {
      PreparedStatement ps = ReloadApp.conn.prepareStatement("select * from CLUSTER where NAME = ? and PASSWORD_HASH = ?");
      ps.setString(1,name);
      ps.setString(2, hashPassword(pw));
      ResultSet rs=ps.executeQuery();
      while(rs.next()){  
      c = new Cluster(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDate(4).toLocalDate(), rs.getString(5));
      LOG.info(c.toString());  
      }  
      ps.close();
      return c;

    } catch (SQLException e) {
        throw new RuntimeException(e);
  
    }
  }
  
  
  public int insert(Cluster cluster) {
    try {
      PreparedStatement ps = ReloadApp.conn.prepareStatement("insert into CLUSTER (ID, GROUP_ID, NAME, CREATE_DATE, PASSWORD_HASH) values(?,?,?,?,?)");
      ps.setInt(1, cluster.getId());
      ps.setInt(2, cluster.getGroup_id());
      ps.setString(3, cluster.getName());
      ps.setDate(4, Date.valueOf(cluster.getCreate_date()));
      ps.setString(5, hashPassword(cluster.getPassword_hash()));
      ps.executeUpdate();
      ps.close();

  } catch (SQLException e) {
      throw new RuntimeException(e);

  } 
    return 1;
    
  }


  private String hashPassword(String input) {
    try {  
      MessageDigest md = MessageDigest.getInstance("SHA-512"); 
      byte[] messageDigest = md.digest(input.getBytes());  
      BigInteger no = new BigInteger(1, messageDigest);  
      String hashtext = no.toString(16); 
      while (hashtext.length() < 32) { 
          hashtext = "0" + hashtext; 
      } 
      return hashtext; 
  } 

  catch (NoSuchAlgorithmException e) { 
      throw new RuntimeException(e); 
  }
    
  }
}
