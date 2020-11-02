package com.exampleapi.logic;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Base64;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.exampleapi.jdbc.Database;
import com.exampleapi.jdbc.daos.ReloadSessionDAO;
import com.exampleapi.model.ConnectionWrapper;
import com.exampleapi.model.PermissionChecker;
import com.exampleapi.model.ReloadSession;
import com.exampleapi.path.PathTest3;
import com.exampleapi.path.message.TestRequest;

@Component
public class ReloadSessionInsertLogic {

  @Autowired
  Database db;
  
  private static final Logger LOG = LogManager.getLogger(ReloadSessionInsertLogic.class.getName());
  
  
  public Integer insertData(String auth, TestRequest testRequest, String targetMSISDN) throws ClassNotFoundException, SQLException, InterruptedException {
    LOG.trace("logic: auth{} req{} msis{}",auth, testRequest,targetMSISDN);
//    Integer access = checkAccess(auth);
    Integer insert = 0;
//    if(access==1) {
      ReloadSession rs = new ReloadSession(testRequest, auth, targetMSISDN);
      insert = insertReloadSession(rs);
      if(insert ==1) {
        LOG.trace("RELOADSESSION insert(): success insert inserted rows :{}", insert );
        return 1;
      }
//    }
//    else {
//      LOG.trace("code 1105 no access access:{}", access );
//  
//    }
    
    return 1;
  }
  
//  private Integer checkAccess(String authorization) throws SQLException, InterruptedException, ClassNotFoundException {
//    LOG.trace("author encoded: {}",authorization);
//    String credentials[] = credentials(authorization);
//    PermissionChecker pc = null;
//    ConnectionWrapper cw = null;
//    int access = 7;
//    try {
//       cw = db.getConnection();
//       pc = new PermissionChecker();
//       access = pc.checkPermission(cw.getConnection(), credentials[0], credentials[1], credentials[2]);
//    }
//    catch(SQLException e) {
//      throw new RuntimeException("something went wrong in the check permission in PathTest3");
//    }
//    catch(NullPointerException e) {
//      throw new RuntimeException("connection is null");
//    }
//    finally {
//      db.releaseConnection(cw);
//    }
//    return access;
//  }
  
//  private String[] credentials(String authorization) {
//    String encoded = authorization.split(" ")[1];
//    byte[] decoded = Base64.getDecoder().decode(encoded.getBytes(StandardCharsets.UTF_8));
//    String decodedString = new String(decoded);
//    LOG.trace("ReloadSessionInsertLogic decoded auth: {}",decodedString);
//    String credentials[] = decodedString.split(":"); 
//    return credentials;
//  }
  
  public Integer insertReloadSession(ReloadSession rs) throws SQLException, InterruptedException, ClassNotFoundException {
    ReloadSessionDAO dao=null;
    ConnectionWrapper cw=null;
    int ret =7;
    try {
      dao = new ReloadSessionDAO();
      cw = db.getConnection();
      ret = dao.insert(rs, cw.getConnection());
      LOG.trace("ReloadSessionInsertLogic:: Inserting row");
    }
    catch(SQLException e) {
      throw new RuntimeException("something went wrong in PathTest3 - inserReloadSesion");
    }
    finally {
      db.releaseConnection(cw);
    }
    return ret;
  }
  //tu cos nie gra
//  private String verifyReloadSession(ReloadSession rs) throws ClassNotFoundException, InterruptedException {
//    ReloadSessionDAO dao=null;
//    ConnectionWrapper cw=null;
//    String ret="7";
//    try {
//      dao = new ReloadSessionDAO();
//      cw = db.getConnection();
//      ret = dao.verify(rs, cw.getConnection());
//    }
//    catch(SQLException e) {
//      throw new RuntimeException("something went wrong in PathTest3 - verifyReloadSesion");
//    }
//    finally {
//      db.releaseConnection(cw);
//    }
//    return ret;
//  }

  public Integer alterData(String auth, TestRequest testRequest, String targetMSISDN, String newMsisdn) throws ClassNotFoundException, SQLException, InterruptedException {
    ReloadSessionDAO dao=null;
    ConnectionWrapper cw=null;
    LOG.trace("alterData: auth{} req{} msis{}",auth, testRequest,targetMSISDN);

      ReloadSession rs = new ReloadSession(testRequest, auth, targetMSISDN);
      int ret=7;
      try {
        dao = new ReloadSessionDAO();
        cw = db.getConnection();
        ret = dao.update(rs, cw.getConnection(), newMsisdn);
        LOG.trace("ReloadSessionInsertLogic:: Updating row");
      }
      catch(RuntimeException e) {
        
      }
      finally {
        db.releaseConnection(cw);
      }
      return ret;
  }
  public Integer updateData(ReloadSession rs) throws ClassNotFoundException, SQLException, InterruptedException {
    ReloadSessionDAO dao=null;
    ConnectionWrapper cw=null;
    LOG.trace("ReloadSessionInsertLogic:: update Data: ReloadSession:{}", rs.toString());

      int ret=7;
      try {
        dao = new ReloadSessionDAO();
        cw = db.getConnection();
        ret = dao.update(rs, cw.getConnection(), rs.getMSISDN());
        LOG.trace("ReloadSessionInsertLogic:: Updating row");
      }
      catch(RuntimeException e) {
        
      }
      finally {
        db.releaseConnection(cw);
      }
      return ret;
  }
  
}
