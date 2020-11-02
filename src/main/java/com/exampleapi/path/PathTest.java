package com.exampleapi.path;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ContainerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.exampleapi.jdbc.DBTest;
import com.exampleapi.jdbc.Database;
import com.exampleapi.jdbc.daos.CltgroupDAOImpl;
import com.exampleapi.jdbc.daos.ClusterDAOImpl;
import com.exampleapi.model.Cltgroup;
import com.exampleapi.model.Cluster;

@Component
@Path("/v1/test")
public class PathTest {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response process(@Context ContainerRequest request) {
    
    String responseEntity = getResponse(22, "Success");
    Response response = Response
        .status(HttpStatus.OK.value())
        .entity(responseEntity)
        .header("myHeader", "/v1/test")
        .build();
   
    return response;
  }
  
  private String getPath(ContainerRequest request) {
    String result = request.getRequestUri().getPath();
    String query = request.getRequestUri().getQuery(); 
    if(query != null) {
      result += "?" + query;
    }
    return result;
  }
  
  private String getResponse(int code, String message) {
    StringBuilder sb = new StringBuilder();
    sb.append("{")
    .append("\"code:\"").append(code).append(",")
    .append("\"message\":").append("\"").append(message).append("\"")
    .append("}");
    return sb.toString();
  }
  
  private String getLoggingInfo(String path, Response response) {
    StringBuilder sb = new StringBuilder();
    sb.append("OUT GET ")
    .append(path)
    .append(" [")
    .append("status:").append(response.getStatus()).append(",")
    .append("reason:").append(response.getStatusInfo()).append(",")
    .append("headers:").append(getHeaders(response.getHeaders())).append(",")
    .append("entity:").append(response.getEntity())
    .append("]");
    return sb.toString();
  }
  
  private String hashPassword(String input) {
    try {  
      MessageDigest md = MessageDigest.getInstance("SHA-512"); 
      byte[] messageDigest = md.digest(input.getBytes());  
      BigInteger no = new BigInteger(1, messageDigest);  
      String hashtext = no.toString(16); 
      while (hashtext.length() < 32) { 
          hashtext = "0" + hashtext; 
      } 
      return hashtext; 
    } 

    catch (NoSuchAlgorithmException e) { 
        throw new RuntimeException(e); 
    }
    
  }
  
  private String getHeaders(MultivaluedMap<String, Object> headers) {
    StringBuilder sb = new StringBuilder();
    for(String key : headers.keySet()) { 
      sb.append(key).append(":").append(headers.get(key)).append(";");
    }
    return sb.toString();
  }
}
