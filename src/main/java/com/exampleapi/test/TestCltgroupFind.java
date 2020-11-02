package com.exampleapi.test;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
import com.exampleapi.model.Cltaccess;
import com.exampleapi.model.Cltgroup;
import com.exampleapi.model.ConnectionWrapper;

@Component
@Path("/cltgroupFind/test")
public class TestCltgroupFind {
  
  @Autowired
  Database db;
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response process(@Context ContainerRequest request) throws SQLException, InterruptedException, ClassNotFoundException {

    String responseEntity = getResponse(22, "Success");
    CltgroupDAOImpl accessDao = new CltgroupDAOImpl();
    ConnectionWrapper cw = db.getConnection();
    Cltgroup c = accessDao.findById(1,cw.getConnection());
    
    Response response = Response
        .status(HttpStatus.OK.value())
        .entity(responseEntity)
        .header("myHeader", "/cltaccess/test")
        .build();
    
    return response;
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
