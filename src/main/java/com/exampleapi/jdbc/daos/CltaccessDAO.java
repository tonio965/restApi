package com.exampleapi.jdbc.daos;

import java.util.List;

import com.exampleapi.model.Cltaccess;
import com.exampleapi.model.Cltgroup;

public interface CltaccessDAO {
  
  public int insert(Cltaccess cltaccess);
  public Cltaccess findByModuleId(int module, int group);
  public int deleteById(int module, int group);
}
