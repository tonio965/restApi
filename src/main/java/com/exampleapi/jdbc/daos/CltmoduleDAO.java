package com.exampleapi.jdbc.daos;

import com.exampleapi.model.Cltmodule;

public interface CltmoduleDAO {
  
  public int insert(Cltmodule cltmodule);
  public Cltmodule findById(int id);
  public int deleteById(int id);

}
