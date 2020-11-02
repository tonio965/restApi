package com.exampleapi.model;

public class Cltgroup {
  
  Integer id;
  String name;
  String description;
  
  
  public Cltgroup(Integer id, String name, String description) {
    super();
    this.id = id;
    this.name = name;
    this.description = description;
  }
  
  public Cltgroup() {
    super();
  }

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "Cltgroup [id=" + id + ", name=" + name + ", description=" + description + "]";
  }
  
  

}
