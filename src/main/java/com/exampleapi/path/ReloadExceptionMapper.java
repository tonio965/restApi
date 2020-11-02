package com.exampleapi.path;


import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.ContainerRequest;
import org.springframework.http.HttpStatus;

import com.exampleapi.path.message.BaseResponse;

public class ReloadExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
  private static final Logger LOG = LogManager.getLogger(ReloadExceptionMapper.class.getName());
  @Context 
  private ContainerRequest request;
  
  @Override
  public Response toResponse(ConstraintViolationException exception) {
    LOG.info("ReloadExceptionMapper.toResponse exception:{}",exception.getMessage());
    BaseResponse br = new BaseResponse(-11, exception.getMessage());
    Response response = Response
        .status(HttpStatus.OK.value())
        .entity(br)
        .header("requestId", request.getRequestHeader("requestID"))
        .build();
    
    return response;
  }

}
