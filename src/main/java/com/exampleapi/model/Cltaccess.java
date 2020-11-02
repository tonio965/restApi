package com.exampleapi.model;

public class Cltaccess {
  Integer module_id;
  Integer group_id;
  Integer permission;
  
  
  public Cltaccess() {
  }
  
  public Cltaccess(Integer module_id, Integer group_id, Integer permission) {
    super();
    this.module_id = module_id;
    this.group_id = group_id;
    this.permission = permission;
  }

  public Integer getModule_id() {
    return module_id;
  }


  public void setModule_id(Integer module_id) {
    this.module_id = module_id;
  }


  public Integer getGroup_id() {
    return group_id;
  }


  public void setGroup_id(Integer group_id) {
    this.group_id = group_id;
  }


  public Integer getPermission() {
    return permission;
  }


  public void setPermission(Integer permission) {
    this.permission = permission;
  }

  @Override
  public String toString() {
    return "Cltaccess [module_id=" + module_id + ", group_id=" + group_id + ", permission=" + permission + "]";
  }
  
  
  
  
  
  
}
