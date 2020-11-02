package com.exampleapi.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ContainerRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.exampleapi.jdbc.daos.CltgroupDAOImpl;
import com.exampleapi.model.Cltgroup;

@Component
@Path("/cltgroupInsert/test")
public class TestCltgroupInsert {
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response process(@Context ContainerRequest request) {

    String responseEntity = getResponse(22, "Success");
    CltgroupDAOImpl dao = new CltgroupDAOImpl();
    Cltgroup c = new Cltgroup(99999,"testnameGroup","descTest");
    int x = dao.insert(c);
    System.out.println(c.toString());
    
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
