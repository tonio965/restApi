package com.exampleapi.logic;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exampleapi.jdbc.Database;
import com.exampleapi.jdbc.daos.ChannelCashTypeDAO;
import com.exampleapi.jdbc.daos.ReloadChannelDAO;
import com.exampleapi.jdbc.daos.ReloadSessionDAO;
import com.exampleapi.model.ChannelCashType;
import com.exampleapi.model.ConnectionWrapper;
import com.exampleapi.model.ErrorEnum;
import com.exampleapi.model.ReloadChannel;
import com.exampleapi.model.ReloadChannelAndCashTypeWrapper;

@Component
public class ReloadChannelLogic {

  @Autowired
  Database db;
  
  private static final Logger LOG = LogManager.getLogger(ReloadSessionInsertLogic.class.getName());
  
  public ErrorEnum checkReloadChannel(String name, String subChannel) throws ClassNotFoundException, InterruptedException {
    ErrorEnum response = null;
    ReloadChannel rc = null;
    ChannelCashType cashType = null;
    ReloadChannelDAO dao=null;
    ChannelCashTypeDAO cashTypeDao=null;
    ConnectionWrapper cw=null;
    try {
      dao = new ReloadChannelDAO();
      cashTypeDao = new ChannelCashTypeDAO();
      cw=db.getConnection();
      rc=dao.readFromName(name, subChannel, cw.getConnection());
      if(rc.getNAME()!=null) { //jezeli jest jakis ReloadChannel odczytany z bazy
      if(!rc.getNAME().equals(name) && !rc.getSUB_CHANNEL().equals(subChannel)) { //jezeli sie nie zgadza z tym o przekazane
          LOG.trace("ReloadChannelLogic: reload channel w/ given data not found");
          return ErrorEnum.configNotFound;
        }
        else { //jezeli sie znalazl taki reloadChannel to pobieram channelCashtype do tego
          cashType=cashTypeDao.getChannelCashType(rc, cw.getConnection());
          LOG.trace("ReloadChannelLogic: reload channel w/ given data found");
        }
        if(cashType==null || rc==null) {
          
          LOG.trace("ReloadChannelLogic: channel cash type w/ given data not found");
          return ErrorEnum.configNotFound;
        }
      }
      else {
        return ErrorEnum.configNotFound;
      }
      
    }
    catch(SQLException e) {
      throw new RuntimeException("no db connection in ReloadSessionVerifyLogic");
    }
    finally {
      db.releaseConnection(cw);
    }
    if(cashType!=null && rc!=null)
      return ErrorEnum.success;
    else {
      return ErrorEnum.configNotFound;
    }
  }
  
  public ReloadChannelAndCashTypeWrapper getReloadChannelAndCashType(String name, String subChannel) throws ClassNotFoundException, InterruptedException {
    ReloadChannelAndCashTypeWrapper wrapper = null;
    ReloadChannel rc = null;
    ChannelCashType cashType = null;
    ReloadChannelDAO dao=null;
    ChannelCashTypeDAO cashTypeDao=null;
    ConnectionWrapper cw=null;
    try {
      wrapper = new ReloadChannelAndCashTypeWrapper();
      dao = new ReloadChannelDAO();
      cashTypeDao = new ChannelCashTypeDAO();
      cw=db.getConnection();
      rc=dao.readFromName(name, subChannel, cw.getConnection());
      if(!rc.getNAME().equals(name) && !rc.getSUB_CHANNEL().equals(subChannel)) { //obudowac trycatchem na nulla
        LOG.trace("ReloadChannelLogic: reload channel w/ given data not found");
      }
      else {
        cashType=cashTypeDao.getChannelCashType(rc, cw.getConnection());
        wrapper.setReloadChannel(rc);
        wrapper.setCashType(cashType);
        LOG.trace("ReloadChannelLogic: reload channel w/ given data found");
      }
      if(cashType.getRELOAD_CHANNEL_ID()!=rc.getID()) {
        LOG.trace("ReloadChannelLogic: channel cash type w/ given data not found");
      }
      
    }
    catch(SQLException e) {
      throw new RuntimeException("no db connection in ReloadSessionVerifyLogic");
    }
    finally {
      db.releaseConnection(cw);
    }
    if(rc.getNAME().equals(name) && rc.getSUB_CHANNEL().equals(subChannel) && cashType.getRELOAD_CHANNEL_ID()==rc.getID())
      return wrapper;
    else {
      return null;
    }
  }
  
  
  
  
}
