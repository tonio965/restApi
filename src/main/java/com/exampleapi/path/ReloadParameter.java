package com.exampleapi.path;

import javax.validation.constraints.NotNull;


public class ReloadParameter {
  
  private
  @NotNull(message="name can't be null")
  String name;
  
  private
  @NotNull(message="value can't be null")
  String value;
  
  

  public ReloadParameter() {
    super();
  }

  public ReloadParameter(String name, String value) {
    super();
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
  
  public String toString() {
    return "name: "+name+" value: "+ value;
  }
  

}
