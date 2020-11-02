package com.exampleapi.path;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ContainerRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Path("/v2/test")
public class PathTest2 {
  public static String name = "";
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response process(@Context ContainerRequest request, String message) {
    
    String path = getPath(request);
    name=message;
    String returnedName = newName();
    String responseEntity = getResponse(22, returnedName);
    name=returnedName;
    Response response = Response
        .status(HttpStatus.OK.value())
        .entity(returnedName)
        .header("myHeader", "ReloadApi")
        .build();
    return response;
  }
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response resp(@Context ContainerRequest request) {
    
    String responseEntity = getResponse(22, name); 
    Response response = Response
        .status(HttpStatus.OK.value())
        .entity(responseEntity)
        .header("myHeader", "ReloadApi")
        .build();
    
    return response;
  }
  
  private String getResponse(int code, String message) {
    StringBuilder sb = new StringBuilder();
    sb.append(message);
    return sb.toString();
  }
  
  private String newName() {
    String newName = name.replaceAll("\\d","");
    int number = Integer.parseInt(name.replaceAll("[^0-9]", "")) + 1;
    StringBuilder sb=new StringBuilder();
    sb.append(newName.substring(0, newName.length()-4))
      .append(number)
      .append(newName.substring(newName.length()-4, newName.length()));
    
    return sb.toString();
    
  }
  
  private String getPath(ContainerRequest request) {
    String result = request.getRequestUri().getPath();
    String query = request.getRequestUri().getQuery(); 
    if(query != null) {
      result += "?" + query;
    }
    return result;
  }

}
