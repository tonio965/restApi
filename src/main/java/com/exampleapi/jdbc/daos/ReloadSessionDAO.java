package com.exampleapi.jdbc.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

import com.exampleapi.logic.ReloadSessionInsertLogic;
import com.exampleapi.logic.ReloadSessionVerifyLogic;
import com.exampleapi.model.ErrorEnum;
import com.exampleapi.model.ReloadSession;
import com.exampleapi.model.ResponseMsisdnWrapper;
import com.exampleapi.path.PathTest3;


@Component
public class ReloadSessionDAO {
  
  @Autowired
  ReloadSessionVerifyLogic verifyLogic;
  
  @Autowired
  ReloadSessionInsertLogic insertLogic;
  

  
  private static final Logger LOG = LogManager.getLogger(ReloadSessionDAO.class.getName());

  public int insert(ReloadSession rs, Connection connection) {
    PreparedStatement ps=null;
    try {
      ps = connection.prepareStatement("INSERT INTO RELOADSESSION (SESSION_ID, "
          + "REQUEST_ID, TRX_TYPE, MSISDN, ACCOUNT, AMOUNT, TRX_STATE, EXPIRY, TARGET, TRANS_ID, "
          + "TRS_ID, DST_ID, ORIGINATOR, CASH_TYPE, CHANNEL_TYPE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)");

      ps.setString(1,rs.getSESSION_ID());
      ps.setString(2, rs.getREQUEST_ID());
      ps.setInt(3, rs.getTRX_TYPE());
      ps.setString(4, rs.getMSISDN());
      ps.setString(5, rs.getACCOUNT());
      ps.setString(6, rs.getAMOUNT());
      ps.setInt(7, rs.getTRX_STATE());
      ps.setTimestamp(8, Timestamp.valueOf(rs.getEXPIRY()));
      ps.setInt(9, rs.getTARGET());
      ps.setInt(10, rs.getTRANS_ID());
      ps.setInt(11, rs.getTRS_ID());
      ps.setInt(12, rs.getDST_ID());
      ps.setString(13, rs.getORIGINATOR());
      if(rs.getCASH_TYPE()==null && rs.getCHANNEL_TYPE()==null ) {
        ps.setNull(14,Types.VARCHAR);
        ps.setNull(15,Types.VARCHAR);
      }
      else {
          ps.setString(14,rs.getCASH_TYPE());
          ps.setString(15,rs.getCHANNEL_TYPE());
      }
      int i=ps.executeUpdate();
      return i;

    } catch (SQLException e) {
      LOG.trace("ReloadSessionDAO:: tried to insert a duplicate row to the db");
      return 0;
//      throw new RuntimeException("Something wrong during insert, probably row like this exists in db (requestId the same)");
//        throw new RuntimeException("something went wrong during insertion of ReloadSession");
    } finally {
      if(ps!=null) {
        try {
          ps.close();
        } catch(SQLException e) {
          e.printStackTrace();
        }
      }
    }

  }
  
  public int update(ReloadSession rs, Connection connection, String newMsisdn) throws SQLException {
    PreparedStatement ps=null;
    ResultSet result = null;
    int update=0;
    try {
      ps = connection.prepareStatement("UPDATE RELOADSESSION "
          + "SET SESSION_ID =?, REQUEST_ID=?, TRX_TYPE=?, MSISDN=?, ACCOUNT=?, "
          + "AMOUNT=?, TRX_STATE=?, EXPIRY=?, TARGET=?, TRANS_ID=?, TRS_ID=?, DST_ID=?, ORIGINATOR=?"
          + "WHERE MSISDN = ? AND TRX_STATE=?"); 
      ps.setString(1,rs.getSESSION_ID());
      ps.setString(2, rs.getREQUEST_ID());
      ps.setInt(3, rs.getTRX_TYPE());
      ps.setString(4, rs.getMSISDN());
      ps.setString(5, rs.getACCOUNT());
      ps.setString(6, rs.getAMOUNT());
      ps.setInt(7, rs.getTRX_STATE());
      ps.setTimestamp(8, Timestamp.valueOf(rs.getEXPIRY()));
      ps.setInt(9, rs.getTARGET());
      ps.setInt(10, rs.getTRANS_ID());
      ps.setInt(11, rs.getTRS_ID());
      ps.setInt(12, rs.getDST_ID());
      ps.setString(13, rs.getORIGINATOR());
      ps.setString(14, newMsisdn);
      ps.setInt(15, rs.getTRX_STATE());
      update = ps.executeUpdate();
      LOG.trace("updated rows: {}", update);
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
    
    return update;
  }
  
  public List<ReloadSession> getNotExpiredSessions(ReloadSession rs, Connection connection){
    List<ReloadSession> sessions = new ArrayList<>();
    LOG.trace("reloadSessionDAO getNotExpiredSessions for {}", rs.toString());
    PreparedStatement ps=null;
    ResultSet result = null;
    try {
      ReloadSession reloadSession = new ReloadSession();
      ps = connection.prepareStatement("SELECT * FROM RELOADSESSION WHERE "+ //zmienic na zbiernie warosci
          "MSISDN=? AND TRX_STATE=? AND TARGET = ? AND REQUEST_ID<>?"); 
      ps.setString(1, rs.getMSISDN());
      ps.setInt(2, rs.getTRX_STATE());
      ps.setInt(3, rs.getTARGET());
      ps.setString(4, rs.getREQUEST_ID());
      result=ps.executeQuery();
      while(result.next()) {
        LOG.trace("from db: {} from test: {}",result.getTimestamp(8).getTime(),System.currentTimeMillis() );
        if(result.getTimestamp(8).getTime()>System.currentTimeMillis() && result.getInt(7)==1) { //to co dodane do bazy ma +600 sekund
          reloadSession.setSESSION_ID(result.getString(1));
          reloadSession.setREQUEST_ID(result.getString(2));
          reloadSession.setTRX_TYPE(result.getInt(3));
          reloadSession.setMSISDN(result.getString(4));
          reloadSession.setACCOUNT(result.getString(5));
          reloadSession.setAMOUNT(result.getString(6));
          reloadSession.setTRX_STATE(result.getInt(7));
          reloadSession.setEXPIRY(result.getTimestamp(8).toLocalDateTime());
          reloadSession.setTARGET(result.getInt(9));
          reloadSession.setTRANS_ID(result.getInt(10));
          reloadSession.setTRS_ID(result.getInt(11));
          reloadSession.setDST_ID(result.getInt(12));
          reloadSession.setORIGINATOR(result.getString(13));
          if(result.getString(14)!=null && result.getString(15)!=null ) {
            reloadSession.setCHANNEL_TYPE(result.getString(14));
            reloadSession.setCASH_TYPE(result.getString(15));
          }
          LOG.trace("reloadSessionDAO getNotExpiredSessions: got SESSION {}",reloadSession.toString());
          sessions.add(reloadSession);
        }
      }
    }
    catch(NullPointerException e) {
    }
    catch(SQLException e) {
      throw new RuntimeException("something went wrong during db connection in permissionChecker");
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
    return sessions;
  }
  
  
  public ResponseMsisdnWrapper verify(ReloadSession rs, Connection connection) throws ClassNotFoundException, SQLException, InterruptedException {
    ResponseMsisdnWrapper wrapper = new ResponseMsisdnWrapper();
    PreparedStatement ps=null;
    ResultSet result = null;
    int trxState=7;
    String msisdn=null;
    int containsTrx=0;
    int containsMsisdn=0;
    LOG.trace("request{} trxtype{}", rs.getREQUEST_ID(), rs.getTRX_TYPE());
    try {
      ps = connection.prepareStatement("SELECT * FROM RELOADSESSION WHERE "+
          "REQUEST_ID=? AND TRX_TYPE=?"); //zmeinic selecta na trxState i na msisdn
      ps.setString(1, rs.getREQUEST_ID());
      ps.setInt(2, rs.getTRX_TYPE());
      result=ps.executeQuery();
      while(result.next()) {
        trxState=result.getInt(7);
        msisdn=result.getString(4);
      }
      LOG.trace("got trx{} got msisdn{}", trxState, msisdn);
      if(trxState==2 || trxState==4 || trxState==5) {
        wrapper.setResponse(ErrorEnum.invalidState);
        return wrapper;
      }
      else {
        if(msisdn!=null) {
          wrapper.setMsisdn(msisdn);
          return wrapper;
        }
        else {
          wrapper.setResponse(ErrorEnum.forbidden);
          return wrapper;
        }
        
      }
    }
    catch(NullPointerException e) {
    }
    catch(SQLException e) {
      throw new RuntimeException("something went wrong during db connection in permissionChecker");
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
    wrapper.setResponse(ErrorEnum.sysError);
    return wrapper;
  }
}
