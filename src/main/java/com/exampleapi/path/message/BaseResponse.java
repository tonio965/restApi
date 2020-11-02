package com.exampleapi.path.message;

public class BaseResponse {
  private Integer code;
  private String message;
  
  public BaseResponse() {}
  
  public BaseResponse(int code, String message) {
    this.code = new Integer(code);
    this.message = message;
  }
  
  public Integer getCode() {
    return code;
  }
  public void setCode(Integer code) {
    this.code = code;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  
  
}
