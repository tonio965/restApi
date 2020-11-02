package com.exampleapi.path.message;


import java.util.Arrays;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import com.exampleapi.path.ReloadParameter;

@Validated
public class TestRequest {
  
  private
  @NotNull(message="message is empty")
  @Size(min=5, max=10, message="message incorrect length!!" )
  @Pattern(regexp = "^\\p{ASCII}{5,10}$", message = "pattern not matched")
  String message;
  
  private
  @NotNull(message="originator is empty")
  @NotBlank(message="no originator is given")
  String originator;
  
  private
  @NotNull(message="amount is empty")
  @Pattern(regexp = "^-?\\d+\\.\\d{2}$", message = "amount pattern not matched eg. 22.22")
  @NotBlank(message="no amount is given")
  String amount;
  
  private
  @NotNull(message="requestId is empty")
  @NotBlank(message="no requestId is given")
  @Valid
  String requestId;
  
  private
  @NotNull(message="subChannel is empty")
  @NotBlank(message="no subChannel is given")
  String subChannel;
  
  
  private 
  @Size(min=7, max=7, message="reconciliationPeriod incorrect length!!" )
  String reconciliationPeriod;
  
  private
  @Valid
  ReloadParameter [] reloadParameters;


  public ReloadParameter[] getReloadParameters() {
    return reloadParameters;
  }


  public void setReloadParameters(ReloadParameter[] reloadParameters) {
    this.reloadParameters = reloadParameters;
  }


  public String getReconciliationPeriod() {
//    Year y = Year.of( Integer.valueOf(reconciliationPeriod.substring(0,4)) ) ;
//    LocalDate ld = y.atDay( Integer.valueOf(reconciliationPeriod.substring(4)) ) ;
//    return ld.toString().replace("-", ".");
    return reconciliationPeriod;
  }


  public void setReconciliationPeriod(String reconciliationPeriod) {
    this.reconciliationPeriod = reconciliationPeriod;
  }


  public String getSubChannel() {
    return subChannel;
  }


  public void setSubChannel(String subChannel) {
    this.subChannel = subChannel;
  }


  public String getAmount() {
    return amount;
  }
  

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
  
  
  public String getOriginator() {
    return originator;
  }

  public void setOriginator(String originator) {
    this.originator = originator;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("TestRequest")
    .append("[")
    .append("message:").append(getMessage())
    .append(" originator:").append(getOriginator())
    .append(" amount:").append(getAmount())
    .append(" requestId:").append(getRequestId())
    .append(" subChannel:").append(getSubChannel())
    .append(" reconciliationPeriod:").append(getReconciliationPeriod())
    .append(" reloadParameters:"+ Arrays.toString(reloadParameters))
    .append("]");
    return sb.toString();
  }
}

