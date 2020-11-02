package com.exampleapi.model;

public class ReloadChannelAndCashTypeWrapper {
  
  ReloadChannel reloadChannel;
  ChannelCashType cashType;
  
  
  public ReloadChannelAndCashTypeWrapper() {
    super();
  }

  public ReloadChannelAndCashTypeWrapper(ReloadChannel reloadChannel, ChannelCashType cashType) {
    super();
    this.reloadChannel = reloadChannel;
    this.cashType = cashType;
  }

  public ReloadChannel getReloadChannel() {
    return reloadChannel;
  }

  public void setReloadChannel(ReloadChannel reloadChannel) {
    this.reloadChannel = reloadChannel;
  }

  public ChannelCashType getCashType() {
    return cashType;
  }

  public void setCashType(ChannelCashType cashType) {
    this.cashType = cashType;
  }
  
  
  
  

}
