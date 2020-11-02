package com.exampleapi.model;

import org.springframework.stereotype.Component;

@Component
public class ReloadChannel {
  private int ID;
  private String NAME;
  private String SUB_CHANNEL;
  private String DESCRIPTION;
  private String CHANNEL_TYPE;
  private int MAX_AMOUNT;
  private int MIN_AMOUNT;
  private int RESERVATION_TIME;
  
  public ReloadChannel() {
    super();
  }
  
  

  public ReloadChannel(int iD, String nAME, String sUB_CHANNEL, String dESCRIPTION, String cHANNEL_TYPE, int mAX_AMOUNT,
      int mIN_AMOUNT, int rESERVATION_TIME) {
    super();
    ID = iD;
    NAME = nAME;
    SUB_CHANNEL = sUB_CHANNEL;
    DESCRIPTION = dESCRIPTION;
    CHANNEL_TYPE = cHANNEL_TYPE;
    MAX_AMOUNT = mAX_AMOUNT;
    MIN_AMOUNT = mIN_AMOUNT;
    RESERVATION_TIME = rESERVATION_TIME;
  }



  public int getID() {
    return ID;
  }

  public void setID(int iD) {
    ID = iD;
  }

  public String getNAME() {
    return NAME;
  }

  public void setNAME(String nAME) {
    NAME = nAME;
  }

  public String getSUB_CHANNEL() {
    return SUB_CHANNEL;
  }

  public void setSUB_CHANNEL(String sUB_CHANNEL) {
    SUB_CHANNEL = sUB_CHANNEL;
  }

  public String getDESCRIPTION() {
    return DESCRIPTION;
  }

  public void setDESCRIPTION(String dESCRIPTION) {
    DESCRIPTION = dESCRIPTION;
  }

  public String getCHANNEL_TYPE() {
    return CHANNEL_TYPE;
  }

  public void setCHANNEL_TYPE(String cHANNEL_TYPE) {
    CHANNEL_TYPE = cHANNEL_TYPE;
  }

  public int getMAX_AMOUNT() {
    return MAX_AMOUNT;
  }

  public void setMAX_AMOUNT(int mAX_AMOUNT) {
    MAX_AMOUNT = mAX_AMOUNT;
  }

  public int getMIN_AMOUNT() {
    return MIN_AMOUNT;
  }

  public void setMIN_AMOUNT(int mIN_AMOUNT) {
    MIN_AMOUNT = mIN_AMOUNT;
  }

  public int getRESERVATION_TIME() {
    return RESERVATION_TIME;
  }

  public void setRESERVATION_TIME(int rESERVATION_TIME) {
    RESERVATION_TIME = rESERVATION_TIME;
  }
  
  
  

}
