package com.exampleapi.jdbc.daos;

import java.sql.Connection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.exampleapi.model.Cltgroup;


@Component
public interface CltgroupDAO {
  
  public int insert(Cltgroup cltgroup);
  public Cltgroup findById(int id, Connection conn);
  public int deleteById(int id);
}
