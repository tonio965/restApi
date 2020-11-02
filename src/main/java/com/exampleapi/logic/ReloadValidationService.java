package com.exampleapi.logic;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exampleapi.jdbc.Database;
import com.exampleapi.jdbc.daos.ReloadSessionDAO;
import com.exampleapi.model.ChannelCashType;
import com.exampleapi.model.ConnectionWrapper;
import com.exampleapi.model.ErrorEnum;
import com.exampleapi.model.ReloadChannel;
import com.exampleapi.model.ReloadChannelAndCashTypeWrapper;
import com.exampleapi.model.ReloadSession;
import com.exampleapi.path.message.TestRequest;

@Component
public class ReloadValidationService {
  
  @Autowired
  Database db;
  
  @Autowired
  ReloadSessionInsertLogic insertLogic;
  
  @Autowired
  ReloadChannelLogic reloadChannelLogic;
  private static final Logger LOG = LogManager.getLogger(ReloadValidationService.class.getName());
  
  
  public ErrorEnum reloadValidation(TestRequest testRequest, String auth, String targetMSISDN) throws ClassNotFoundException, InterruptedException, SQLException {
    ErrorEnum response;
    int ramsMaxBalance = db.rams.getMax_balance();
    double maximumPossibleReload=0;
    double combinedAmount =0;
    ReloadSessionDAO dao = null;
    ConnectionWrapper cw=null;
    ReloadSession rs = new ReloadSession(testRequest, auth, targetMSISDN);
    List<ReloadSession> sessions = new ArrayList<>();
      try {
        dao = new ReloadSessionDAO();
        cw=db.getConnection();
        sessions=dao.getNotExpiredSessions(rs, cw.getConnection());
      }
      catch(RuntimeException e) {
        
      }
      finally {
        LOG.trace("ReloadValidationService:: reloadValidation() got sessions: {}", sessions.size());
        db.releaseConnection(cw);
      }
      for(ReloadSession reloadSess : sessions) {
        combinedAmount+=Double.valueOf(reloadSess.getAMOUNT());
      }
      ReloadChannelAndCashTypeWrapper wrapper = getReloadChannel(testRequest, auth, targetMSISDN);
      ReloadChannel reloadChannel= wrapper.getReloadChannel();
      ChannelCashType cashType = wrapper.getCashType();
      if(ramsMaxBalance!=0) {
        maximumPossibleReload=ramsMaxBalance-db.rams.getSaldo()-combinedAmount;
        LOG.trace("rs.getamount: {} reloadCannel.min {}", rs.getAMOUNT(), reloadChannel.getMIN_AMOUNT());
        if(Double.valueOf(rs.getAMOUNT())<maximumPossibleReload) {
          if(Double.valueOf(rs.getAMOUNT())>reloadChannel.getMIN_AMOUNT()) { //if is bigger (means that you can add some money)
            if(Double.valueOf(rs.getAMOUNT())<returnSmaller(maximumPossibleReload, reloadChannel.getMAX_AMOUNT())) { //and can be added (less than the smaller of the two)
              rs.setEXPIRY(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()+reloadChannel.getRESERVATION_TIME()), ZoneId.systemDefault()));
              rs.setTARGET(db.rams.getSISstackID());
              rs.setTRS_ID(db.rams.getTrs_id());
              rs.setDST_ID(db.rams.getDst_id());
              rs.setCHANNEL_TYPE(reloadChannel.getCHANNEL_TYPE());
              rs.setCASH_TYPE(cashType.getCASH_TYPE());
              int insertSuccess=insertLogic.insertReloadSession(rs);
              if(insertSuccess!=0)
                response=ErrorEnum.success;
              else {
                response=ErrorEnum.storeSessionError;
              }
            }
            else {
              response=ErrorEnum.reloadTooLow;
            }
            
          }
          else {
            response=ErrorEnum.reloadTooHigh;
          }
        }
        else {
          response=ErrorEnum.balanceExceeded;
        }
      }
      else {
        LOG.trace("rs.getamount: {} reloadCannel.min {}", rs.getAMOUNT(), reloadChannel.getMIN_AMOUNT());
        if(Double.valueOf(rs.getAMOUNT())>reloadChannel.getMIN_AMOUNT()) { //if is bigger (means that you can add some money)
          if(Double.valueOf(rs.getAMOUNT())<reloadChannel.getMAX_AMOUNT()) { //and can be added (less than the smaller of the two)
            rs.setEXPIRY(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()+reloadChannel.getRESERVATION_TIME()), ZoneId.systemDefault()));
            rs.setTARGET(db.rams.getSISstackID());
            rs.setTRS_ID(db.rams.getTrs_id());
            rs.setDST_ID(db.rams.getDst_id());
            rs.setCHANNEL_TYPE(reloadChannel.getCHANNEL_TYPE());
            rs.setCASH_TYPE(cashType.getCASH_TYPE());
            insertLogic.insertReloadSession(rs);
            response=ErrorEnum.success;
          }

          else {
            response=ErrorEnum.reloadTooHigh;
          }
          
        }
        else {
          response=ErrorEnum.reloadTooLow;
        }
      }


    
    
    
    return response;
  }

  public ErrorEnum checkReloadChannel(TestRequest testRequest, String auth, String targetMSISDN) throws ClassNotFoundException, InterruptedException {
    String[] creds = credentials(auth);
    ErrorEnum verification = reloadChannelLogic.checkReloadChannel(creds[0], testRequest.getSubChannel());
    return verification;
  }
  
  private double returnSmaller(double maxPossibleReload, double rsMax) {
    if(maxPossibleReload>rsMax)
      return rsMax;
    else {
      return maxPossibleReload;
    }
  }
  
  public ReloadChannelAndCashTypeWrapper getReloadChannel(TestRequest testRequest, String auth, String targetMSISDN) throws ClassNotFoundException, InterruptedException {
    String[] creds = credentials(auth);
    ReloadChannelAndCashTypeWrapper wrapper= reloadChannelLogic.getReloadChannelAndCashType(creds[0], testRequest.getSubChannel()); //stworzyc wrapper
    return wrapper;
  }
  
  private String[] credentials(String authorization) {
    String encoded = authorization.split(" ")[1];
    byte[] decoded = Base64.getDecoder().decode(encoded.getBytes(StandardCharsets.UTF_8));
    String decodedString = new String(decoded);
    LOG.trace("ReloadSessionInsertLogic decoded auth: {}",decodedString);
    String credentials[] = decodedString.split(":"); 
    return credentials;
  }

}
