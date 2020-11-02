package com.exampleapi;

import javax.ws.rs.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.exampleapi.jdbc.daos.ReloadSessionDAO;
import com.exampleapi.logic.ReloadSessionInsertLogic;
import com.exampleapi.logic.ReloadSessionVerifyLogic;
import com.exampleapi.path.PathError;
import com.exampleapi.path.PathTest;
import com.exampleapi.path.PathTest2;
import com.exampleapi.path.PathTest3;
import com.exampleapi.path.ReloadExceptionMapper;
import com.exampleapi.test.TestCltaccess;
import com.exampleapi.test.TestCltaccessDelete;
import com.exampleapi.test.TestCltaccessFind;
import com.exampleapi.test.TestCltgroupFind;
import com.exampleapi.test.TestCltgroupInsert;

@Component
public class ReloadResourceConfig extends ResourceConfig {
  public static final Logger LOG = LogManager.getLogger(ReloadApp.class);
  public ReloadResourceConfig() {
    loadPath(PathTest.class);
    loadPath(PathTest2.class);
    loadPath(PathTest3.class);
    loadPath(PathError.class);
    loadPath(TestCltaccess.class);
    loadPath(TestCltaccessDelete.class);
    loadPath(TestCltaccessFind.class);
    loadPath(TestCltgroupFind.class);
    loadPath(TestCltgroupInsert.class);
    register(ReloadExceptionMapper.class);
    loadPath(ReloadSessionInsertLogic.class);
    loadPath(ReloadSessionVerifyLogic.class);
    loadPath(ReloadSessionDAO.class);
    register(ReloadSessionDAO.class);

  }
  
  private void loadPath(Class<?> klaz) {
    register(klaz);
    showPathInfo(klaz);
  }
  
  private void showPathInfo(Class<?> klaz) {
    Path path = klaz.getAnnotation(Path.class);
    if(path != null) {
      System.out.println(path.value()+" path loaded");
    }
  }
}
