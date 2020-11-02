package com.exampleapi.path.message;

import java.util.Arrays;

public class ReloadSessionResponse {
  int [] codes;
  int httpStatus;
  
  
  
  public ReloadSessionResponse(int[] codes) {
    super();
    this.codes = Arrays.copyOf(codes, codes.length-1);
    this.httpStatus = codes.length-1;
  }
  public int[] getCodes() {
    return codes;
  }
  public void setCodes(int[] codes) {
    this.codes = codes;
  }
  public int getHttpStatus() {
    return httpStatus;
  }
  public void setHttpStatus(int httpStatus) {
    this.httpStatus = httpStatus;
  }
  @Override
  public String toString() {
    return "ReloadSessionResponse [codes=" + Arrays.toString(codes)+"]";
  }
  
  
  
  
}
