package com.exampleapi.test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ContainerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.exampleapi.jdbc.Database;
import com.exampleapi.jdbc.daos.CltaccessDAOImpl;
import com.exampleapi.jdbc.daos.CltgroupDAOImpl;
import com.exampleapi.jdbc.daos.ClusterDAOImpl;
import com.exampleapi.model.Cltaccess;
import com.exampleapi.model.Cltgroup;
import com.exampleapi.model.Cluster;
import com.exampleapi.model.ConnectionWrapper;
import com.exampleapi.model.PermissionChecker;

@Component
@Path("/cltaccessInsert/test")
public class TestCltaccess {
  
  @Autowired
  public Database db;
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response process( @Context ContainerRequest request, @HeaderParam("authorization") String authorization) throws SQLException, InterruptedException, ClassNotFoundException {
    
    String credentials[] = authorization.split(":"); 
    String name = credentials[0]; 
    String pass = hashPassword(credentials[1]);
    String module = credentials[2];
    StringBuilder responseEntity = new StringBuilder();
    PermissionChecker pc = new PermissionChecker();
    ConnectionWrapper cw = db.getConnection();
    int access = pc.checkPermission(cw.getConnection(), name, pass, module);
    db.releaseConnection(cw);
    Response response = Response
        .status(HttpStatus.OK.value())
        .entity(responseEntity.toString())
        .header("permission","value: "+access)
        .build();
    

    return response;
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
  
  private String getResponse(int code, String message) {
    StringBuilder sb = new StringBuilder();
    sb.append("{")
    .append("\"code:\"").append(code).append(",")
    .append("\"message\":").append("\"").append(message).append("\"")
    .append("}");
    return sb.toString();
  }
}
