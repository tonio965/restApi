package com.exampleapi.path;

import javax.servlet.RequestDispatcher;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.glassfish.jersey.server.ContainerRequest;
import org.springframework.stereotype.Component;

import com.exampleapi.path.message.BaseResponse;

@Component
@Path("/error")
public class PathError {
  private static final Logger LOG = LogManager.getLogger(PathError.class.getName());
  
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Response errorPost(
      @Context HttpHeaders headers,
      @Context ContainerRequest request) {
    
    return handleError("POST", request);
  }
  
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public Response errorPut(
      @Context HttpHeaders headers,
      @Context ContainerRequest request) {
    return handleError("PUT", request);
  }

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  public Response errorDelete(
      @Context HttpHeaders headers,
      @Context ContainerRequest request) {
    return handleError("DELETE", request);
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response errorGet(
      @Context HttpHeaders headers,
      @Context ContainerRequest request) {
    return handleError("GET", request);
  }
  
  private Response handleError(String method, ContainerRequest request) {
    int httpStatus = HttpStatus.INTERNAL_SERVER_ERROR_500;
    String message = request.getProperty(RequestDispatcher.ERROR_MESSAGE).toString();
    try {
      httpStatus = Integer.parseInt(request.getProperty(RequestDispatcher.ERROR_STATUS_CODE).toString());
    } catch(NumberFormatException e) {
      LOG.info("PathError.handleError NumberFormatException:{}", e.getMessage());
    }
    
    LOG.info("IN {} /error status:{}, message:{}", method, httpStatus, message);
    
    BaseResponse baseResponse = new BaseResponse(-453, message);
    Response response = Response
        .status(httpStatus)
        .entity(baseResponse)
        .build();
    
    return response;
  }
}


