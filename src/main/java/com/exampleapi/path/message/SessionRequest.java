package com.exampleapi.path.message;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

@Validated
public class SessionRequest {
  
  
  private String sessionId;
  
  private  //fullpartnername - request id
  @NotNull(message="requestId is empty")
  @NotBlank(message="no requestId is given")
  @Valid
  String requestId;
  
  private Integer trx_type;
  
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
  @NotNull(message="subChannel is empty")
  @NotBlank(message="no subChannel is given")
  String subChannel;

}
