package com.exampleapi.model;

import org.springframework.stereotype.Component;

@Component
public class ChannelCashType {

  int RELOAD_CHANNEL_ID;
  String CASH_TYPE;
  String CASH_NAME;
  
  
  public ChannelCashType() {
    super();
  }
  
  public ChannelCashType(int rELOAD_CHANNEL_ID, String cASH_TYPE, String cASH_NAME) {
    super();
    RELOAD_CHANNEL_ID = rELOAD_CHANNEL_ID;
    CASH_TYPE = cASH_TYPE;
    CASH_NAME = cASH_NAME;
  }
  public int getRELOAD_CHANNEL_ID() {
    return RELOAD_CHANNEL_ID;
  }
  public void setRELOAD_CHANNEL_ID(int rELOAD_CHANNEL_ID) {
    RELOAD_CHANNEL_ID = rELOAD_CHANNEL_ID;
  }
  public String getCASH_TYPE() {
    return CASH_TYPE;
  }
  public void setCASH_TYPE(String cASH_TYPE) {
    CASH_TYPE = cASH_TYPE;
  }
  public String getCASH_NAME() {
    return CASH_NAME;
  }
  public void setCASH_NAME(String cASH_NAME) {
    CASH_NAME = cASH_NAME;
  }

  @Override
  public String toString() {
    return "ChannelCashType [RELOAD_CHANNEL_ID=" + RELOAD_CHANNEL_ID + ", CASH_TYPE=" + CASH_TYPE + ", CASH_NAME="
        + CASH_NAME + "]";
  }
  
  
  
}
