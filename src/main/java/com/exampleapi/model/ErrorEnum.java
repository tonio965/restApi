package com.exampleapi.model;

public enum ErrorEnum {
  
  balanceExceeded("Message: MSISDN maximum balance reached or would be exceeded",1419,403),
  reloadTooHigh("Message: Reload too high",1210,403),
  reloadTooLow("Message: Reload too low",1211,403),
  configNotFound("Message: Channel configuration not found",1702,500),
  invalidState("Message: Invalid session state",1113,404),
  forbidden("Message: Session forbidden",1112,403),
  authFailed("Message: User Authorization failed",1105,401),
  malParameter("Message: Mal parameter",1101,400),
  sysError("Message: Common system error",1100,500),
  success("Message: success", 1001,201),
  storeSessionError("Message: could not insert ReloadSession to the db",1100,500);
  
  public final String message;
  public final int errorCode;
  public final int httpCode;
  
  private ErrorEnum(String message, int errorCode, int httpCode) {
    this.message = message;
    this.errorCode = errorCode;
    this.httpCode = httpCode;
  }

  public String getMessage() {
    return message;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public int getHttpCode() {
    return httpCode;
  }
  
  
  
  
  




}
