package com.exampleapi.model;

public class ResponseMsisdnWrapper {
  
  ErrorEnum response;
  String msisdn;
  
  public ErrorEnum getResponse() {
    return response;
  }
  public void setResponse(ErrorEnum response) {
    this.response = response;
  }
  public String getMsisdn() {
    return msisdn;
  }
  public void setMsisdn(String msisdn) {
    this.msisdn = msisdn;
  }
  
  
  
  

}
