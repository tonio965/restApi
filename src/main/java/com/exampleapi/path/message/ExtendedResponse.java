package com.exampleapi.path.message;

import java.util.UUID;

import com.exampleapi.model.UUIDGenerator;

public class ExtendedResponse extends BaseResponse{
  
  private String RequestID;
  private String authorization;
  private String minAllowedReload, maxAllowedReload;
//  @Context 
//  private ContainerRequest request;

  public String getRequestID() {
    return RequestID;
  }

  public void setRequestID(String requestID) {
    RequestID = requestID;
  }

  public String getMinAllowedReload() {
    return minAllowedReload;
  }

  public void setMinAllowedReload(String minAllowedReload) {
    this.minAllowedReload = minAllowedReload;
  }

  public String getMaxAllowedReload() {
    return maxAllowedReload;
  }

  public void setMaxAllowedReload(String maxAllowedReload) {
    this.maxAllowedReload = maxAllowedReload;
  }

  public String getAuthorization() {
    return authorization;
  }

  public void setAuthorization(String authorization) {
    this.authorization = authorization;
  }

  public ExtendedResponse() {
    super();
  }

  public ExtendedResponse(int code, String message, String RequestID, String authorization,String minAllowedReload, String maxAllowedReload) {
    super(code, message);
    if(RequestID!=null) 
      this.RequestID=RequestID;
    else {
      this.RequestID= new UUIDGenerator().getGeneratedUUID();
    }
    
    this.authorization=authorization;
    this.minAllowedReload=minAllowedReload;
    this.maxAllowedReload=maxAllowedReload;
    // TODO Auto-generated constructor stub
  }
  
  
  
}
