package com.exampleapi.path;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.glassfish.jersey.server.ContainerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.exampleapi.jdbc.Database;
import com.exampleapi.jdbc.daos.ReloadSessionDAO;
import com.exampleapi.logic.ReloadChannelLogic;
import com.exampleapi.logic.ReloadSessionInsertLogic;
import com.exampleapi.logic.ReloadSessionVerifyLogic;
import com.exampleapi.logic.ReloadValidationService;
import com.exampleapi.model.ConnectionWrapper;
import com.exampleapi.model.ErrorEnum;
import com.exampleapi.model.PermissionChecker;
import com.exampleapi.model.ReloadSession;
import com.exampleapi.model.ResponseCodeReader;
import com.exampleapi.model.UUIDGenerator;
import com.exampleapi.path.message.ExtendedResponse;
import com.exampleapi.path.message.ReloadSessionResponse;
import com.exampleapi.path.message.TestRequest;



@Component
@Path("v1/MSISDNs/{targetMSISDN}/reloads/reloadByPartner")
public class PathTest3 {
  
  @Autowired
  Database db;
  
  @Autowired
  ReloadSessionVerifyLogic verifyLogic;
  
  @Autowired
  ReloadSessionInsertLogic insertLogic;
  
  @Autowired
  ReloadChannelLogic reloadChannelLogic;
  
  @Autowired
  ReloadValidationService reloadValidationService;
  
 
  
  private static final Logger LOG = LogManager.getLogger(PathTest3.class.getName());
    
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response process(
      @Context ContainerRequest request,
      
      @PathParam("targetMSISDN")
      @Size(min=11, max=15, message="targetMSISDN incorrect length!!" )
      String targetMSISDN,
      
      @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}", message = "not a UUID")
      @HeaderParam("requestID")
      String reqId,
      
      @HeaderParam("Authorization")
      String auth,
      
      @Valid @RequestBody TestRequest testRequest) throws SQLException, InterruptedException, ClassNotFoundException {
    
        Response response=null;
        if(reqId==null) 
          reqId= new UUIDGenerator().getGeneratedUUID();
        ThreadContext.put("msisdn", targetMSISDN);
        ThreadContext.put("reqId", reqId);
        LOG.trace("IN POST reqId:{} authorization: {} v1/MSISDNs {}/test {}", reqId, auth, targetMSISDN, testRequest);
//        ErrorEnum verification =verifyLogic.verifyReloadSession(testRequest, auth, targetMSISDN);


        ErrorEnum verification = reloadValidationService.checkReloadChannel(testRequest,auth, targetMSISDN);
        if(verification.equals(ErrorEnum.success))
          verification = reloadValidationService.reloadValidation(testRequest, auth, targetMSISDN);
//        ValidateSubscriberResponse vsr = client.getData();
//        LOG.trace("xxxx: {}", vsr.toString());
        LOG.trace("pathtest3 verification: {}", verification);
        response = Response
            .status(verification.getHttpCode())
            .entity(verification.getMessage())
            .header("myHeader", "ReloadApi")
            .build();
        return response;
  }
  
}
