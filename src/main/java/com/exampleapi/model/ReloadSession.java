package com.exampleapi.model;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.annotation.Validated;

import com.exampleapi.path.PathTest3;
import com.exampleapi.path.message.TestRequest;

@Validated
public class ReloadSession {
  
  private static final Logger LOG = LogManager.getLogger(PathTest3.class.getName());
  
  private String SESSION_ID; //generated uuid
  private String REQUEST_ID;
  private Integer TRX_TYPE;
  private String MSISDN;
  private String ACCOUNT;
  private  String AMOUNT;
  private Integer TRX_STATE;
  private LocalDateTime EXPIRY;
  private Integer TARGET;
  private Integer TRANS_ID;
  private Integer TRS_ID;
  private Integer DST_ID;
  private String ORIGINATOR;
  private String SUBCHANNEL;
  private String CHANNEL_TYPE;
  private String CASH_TYPE;
  
  
  
  public ReloadSession() {
    super();
  }
  
  public ReloadSession(TestRequest test, String auth, String MSISDN) throws SQLException, InterruptedException {
    this.REQUEST_ID=test.getRequestId();
    this.AMOUNT=test.getAmount();
    this.ORIGINATOR=test.getOriginator();
    this.SUBCHANNEL=test.getSubChannel();
    setMissingInformation(auth, MSISDN);
  }
  
  public void setMissingInformation(String auth, String MSISDN) throws SQLException, InterruptedException {
    this.SESSION_ID=new UUIDGenerator().getGeneratedUUID();
    StringBuilder sb = new StringBuilder();
    sb.append(GenerateFullPartnerName(auth)).append("-").append(this.REQUEST_ID);
    this.REQUEST_ID=sb.toString();
    this.ACCOUNT=GenerateFullPartnerName(auth);
    this.TRX_TYPE=0;
    this.MSISDN=MSISDN;
    this.EXPIRY= LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()+600000), 
        TimeZone.getDefault().toZoneId());
    this.TARGET=0;
    this.TRANS_ID=0;
    this.DST_ID=0;
    this.TRS_ID=0;
    this.TRX_STATE=1;
    this.CHANNEL_TYPE=null;
    this.CASH_TYPE=null;
    
  }
  
  private String GenerateFullPartnerName(String auth) throws SQLException, InterruptedException {
    String credentials[] = credentials(auth);
    StringBuilder sb = new StringBuilder();
    String fullPartnerName = sb.append(credentials[0]).append("_").append(SUBCHANNEL).toString();
    LOG.info("fullpartner: {}",fullPartnerName);  
    return fullPartnerName;
  }
  
  private String[] credentials(String authorization) {
    String encoded = authorization.split(" ")[1];
    byte[] decoded = Base64.getDecoder().decode(encoded.getBytes(StandardCharsets.UTF_8));
    String decodedString = new String(decoded);
    LOG.info("decoded: {}",decodedString);
    String credentials[] = decodedString.split(":"); 
    return credentials;
  }
  
  

  public String getSESSION_ID() {
    return SESSION_ID;
  }

  public void setSESSION_ID(String sESSION_ID) {
    SESSION_ID = sESSION_ID;
  }

  public String getREQUEST_ID() {
    return REQUEST_ID;
  }

  public void setREQUEST_ID(String rEQUEST_ID) {
    REQUEST_ID = rEQUEST_ID;
  }

  public Integer getTRX_TYPE() {
    return TRX_TYPE;
  }

  public void setTRX_TYPE(Integer tRX_TYPE) {
    TRX_TYPE = tRX_TYPE;
  }

  public String getMSISDN() {
    return MSISDN;
  }

  public void setMSISDN(String mSISDN) {
    MSISDN = mSISDN;
  }

  public String getACCOUNT() {
    return ACCOUNT;
  }

  public void setACCOUNT(String aCCOUNT) {
    ACCOUNT = aCCOUNT;
  }

  public String getAMOUNT() {
    return AMOUNT;
  }

  public void setAMOUNT(String aMOUNT) {
    AMOUNT = aMOUNT;
  }

  public Integer getTRX_STATE() {
    return TRX_STATE;
  }

  public void setTRX_STATE(Integer tRX_STATE) {
    TRX_STATE = tRX_STATE;
  }

  public LocalDateTime getEXPIRY() {
    return EXPIRY;
  }

  public void setEXPIRY(LocalDateTime eXPIRY) {
    EXPIRY = eXPIRY;
  }

  public Integer getTARGET() {
    return TARGET;
  }

  public void setTARGET(Integer tARGET) {
    TARGET = tARGET;
  }

  public Integer getTRANS_ID() {
    return TRANS_ID;
  }

  public void setTRANS_ID(Integer tRANS_ID) {
    TRANS_ID = tRANS_ID;
  }

  public Integer getTRS_ID() {
    return TRS_ID;
  }

  public void setTRS_ID(Integer tRS_ID) {
    TRS_ID = tRS_ID;
  }

  public Integer getDST_ID() {
    return DST_ID;
  }

  public void setDST_ID(Integer dST_ID) {
    DST_ID = dST_ID;
  }

  public String getORIGINATOR() {
    return ORIGINATOR;
  }

  public void setORIGINATOR(String oRIGINATOR) {
    ORIGINATOR = oRIGINATOR;
  }

  public String getSUBCHANNEL() {
    return SUBCHANNEL;
  }

  public void setSUBCHANNEL(String sUBCHANNEL) {
    SUBCHANNEL = sUBCHANNEL;
  }
  

  public String getCHANNEL_TYPE() {
    return CHANNEL_TYPE;
  }

  public void setCHANNEL_TYPE(String cHANNEL_TYPE) {
    CHANNEL_TYPE = cHANNEL_TYPE;
  }

  public String getCASH_TYPE() {
    return CASH_TYPE;
  }

  public void setCASH_TYPE(String cASH_TYPE) {
    CASH_TYPE = cASH_TYPE;
  }

  public static Logger getLog() {
    return LOG;
  }

  @Override
  public String toString() {
    return "ReloadSession [SESSION_ID=" + SESSION_ID + ", REQUEST_ID=" + REQUEST_ID + ", TRX_TYPE=" + TRX_TYPE
        + ", MSISDN=" + MSISDN + ", ACCOUNT=" + ACCOUNT + ", AMOUNT=" + AMOUNT + ", TRX_STATE=" + TRX_STATE
        + ", EXPIRY=" + EXPIRY + ", TARGET=" + TARGET + ", TRANS_ID=" + TRANS_ID + ", TRS_ID=" + TRS_ID + ", DST_ID="
        + DST_ID + ", ORIGINATOR=" + ORIGINATOR + ", SUBCHANNEL=" + SUBCHANNEL + ", CHANNEL_TYPE=" + CHANNEL_TYPE
        + ", CASH_TYPE=" + CASH_TYPE + "]";
  }


  
  
  
  
  

  
  
  
}
