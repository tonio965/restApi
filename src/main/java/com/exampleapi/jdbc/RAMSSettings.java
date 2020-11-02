package com.exampleapi.jdbc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RAMSSettings {
  
  @Value("${rams.sis_stack_id}")
  private int SISstackID;
  
  @Value("${rams.trs_id}")
  private int trs_id;
  
  @Value("${rams.dst_id}")
  private int dst_id;
  
  @Value("${rams.max_balance}")
  private int max_balance;
  
  @Value("${rams.saldo}")
  private int saldo;

  public int getSISstackID() {
    return SISstackID;
  }

  public void setSISstackID(int sISstackID) {
    SISstackID = sISstackID;
  }

  public int getTrs_id() {
    return trs_id;
  }

  public void setTrs_id(int trs_id) {
    this.trs_id = trs_id;
  }

  public int getDst_id() {
    return dst_id;
  }

  public void setDst_id(int dst_id) {
    this.dst_id = dst_id;
  }

  public int getMax_balance() {
    return max_balance;
  }

  public void setMax_balance(int max_balance) {
    this.max_balance = max_balance;
  }

  public int getSaldo() {
    return saldo;
  }

  public void setSaldo(int saldo) {
    this.saldo = saldo;
  }

  @Override
  public String toString() {
    return "RAMSSettings [SISstackID=" + SISstackID + ", trs_id=" + trs_id + ", dst_id=" + dst_id + ", max_balance="
        + max_balance + ", saldo=" + saldo + ", getSISstackID()=" + getSISstackID() + ", getTrs_id()=" + getTrs_id()
        + ", getDst_id()=" + getDst_id() + ", getMax_balance()=" + getMax_balance() + ", getSaldo()=" + getSaldo()
        + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
  }
  
  
  
  
}
