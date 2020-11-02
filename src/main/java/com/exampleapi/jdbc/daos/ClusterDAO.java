package com.exampleapi.jdbc.daos;

import com.exampleapi.model.Cltgroup;
import com.exampleapi.model.Cluster;

public interface ClusterDAO {
  
  public int insert(Cluster cluster);
  public Cluster findById(String name, String pw);

}
