package com.exampleapi.logic;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.core.Response;

import com.exampleapi.jdbc.Database;
import com.exampleapi.jdbc.daos.ReloadSessionDAO;
import com.exampleapi.model.ConnectionWrapper;
import com.exampleapi.model.ErrorEnum;
import com.exampleapi.model.PermissionChecker;
import com.exampleapi.model.ReloadSession;
import com.exampleapi.model.ResponseMsisdnWrapper;
import com.exampleapi.path.message.TestRequest;

@Component
public class ReloadSessionVerifyLogic {
  
  private static final Logger LOG = LogManager.getLogger(ReloadSessionVerifyLogic.class.getName());
  
  @Autowired
  Database db;
  
  @Autowired 
  ReloadSessionInsertLogic insertLogic;
  
  private Integer checkAccess(String authorization) throws SQLException, InterruptedException, ClassNotFoundException {
//    LOG.info("author encoded: {}",authorization);
    String credentials[] = credentials(authorization);
    PermissionChecker pc = null;
    ConnectionWrapper cw = null;
    int access = 7;
    boolean connFailed= false;
    try {
       cw = db.getConnection();
       pc = new PermissionChecker();
       access = pc.checkPermission(cw.getConnection(), credentials[0], credentials[1], credentials[2]);
    }
    catch(SQLException e) {
      connFailed=true;
      throw new RuntimeException("something went wrong in the check permission in ReloadSessionVerifyLogic");
    }
    catch(NullPointerException e) {
      throw new RuntimeException("connection is null");
    }
    finally {
      if(connFailed) {
        // connection failed 
        db.invalidConnection(cw);
      }
      else {
        db.releaseConnection(cw);
      }
        
    }
    return access;
  }
  
  public ErrorEnum verifyReloadSession(TestRequest testRequest, String auth, String targetMSISDN) throws ClassNotFoundException, InterruptedException, SQLException {
    List<Integer> codes = new ArrayList<>();
    ReloadSession rs=null;
    int access=checkAccess(auth);
    if(access==1) {
      LOG.trace("ReloadSessionVerifyLogic: access to db granted");
      rs = new ReloadSession(testRequest, auth, targetMSISDN);
      ReloadSessionDAO dao=null;
      ConnectionWrapper cw=null;
      ResponseMsisdnWrapper ret= new ResponseMsisdnWrapper();
      try {
        dao = new ReloadSessionDAO();
        cw = db.getConnection();
        ret = dao.verify(rs, cw.getConnection());
        if(ret.getResponse()!=null) {
          if(ret.getResponse().equals(ErrorEnum.invalidState)) { // reloadSesion not found in db
            LOG.trace("ReloadSessionVerifyLogic:: invalid state");
  //          insertLogic.insertData(auth, testRequest, targetMSISDN); //insert data
            return ErrorEnum.invalidState;
          }
          if(ret.getResponse().equals(ErrorEnum.forbidden)) {
            LOG.trace("ReloadSessionVerifyLogic: ReloadSessionNotFound in db - inserting data");
            insertLogic.insertData(auth, testRequest, targetMSISDN);
            return ErrorEnum.success;
          }
        }
        if(ret.getMsisdn()!=null){
          LOG.trace("ReloadSessionVerifyLogic: reload session not verified");
//          return buildResponse(codes, 201);
          insertLogic.alterData(auth, testRequest, targetMSISDN, ret.getMsisdn());
          return ErrorEnum.success;
        }
      }
      catch(SQLException e) {
        throw new RuntimeException("no db connection in ReloadSessionVerifyLogic");
      }
      finally {
        db.releaseConnection(cw);
      }
      return ErrorEnum.sysError;
    }
    else {
      LOG.trace("ReloadSessionVerifyLogic: no access to db");
//      return buildResponse(codes, 401);
      return ErrorEnum.authFailed;
    }
    
  }
  private String[] credentials(String authorization) {
    String encoded = authorization.split(" ")[1];
    byte[] decoded = Base64.getDecoder().decode(encoded.getBytes(StandardCharsets.UTF_8));
    String decodedString = new String(decoded);
    String credentials[] = decodedString.split(":"); 
    return credentials;
  }

}
