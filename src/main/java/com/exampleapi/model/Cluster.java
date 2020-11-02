package com.exampleapi.model;

import java.time.LocalDate;
import java.util.Date;

public class Cluster {
  Integer id;
  Integer group_id;
  String name;
  LocalDate create_date;
  String password_hash;
  

  public Cluster() {
    super();
  }

  public Cluster(Integer id, Integer group_id, String name, LocalDate create_date, String password_hash) {
    super();
    this.id = id;
    this.group_id = group_id;
    this.name = name;
    this.create_date = create_date;
    this.password_hash = password_hash;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getGroup_id() {
    return group_id;
  }

  public void setGroup_id(Integer group_id) {
    this.group_id = group_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getCreate_date() {
    return create_date;
  }

  public void setCreate_date(LocalDate create_date) {
    this.create_date = create_date;
  }

  public String getPassword_hash() {
    return password_hash;
  }

  public void setPassword_hash(String password_hash) {
    this.password_hash = password_hash;
  }
  
  @Override
  public String toString() {
    return "Cluster [id=" + id + ", group_id=" + group_id + ", name=" + name + ", create_date=" + create_date
        + ", password_hash=" + password_hash + "]";
  }
  
  

}
