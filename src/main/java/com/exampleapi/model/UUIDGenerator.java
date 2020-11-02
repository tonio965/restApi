package com.exampleapi.model;

import java.util.UUID;

public class UUIDGenerator {
    String generatedUUID;

    public UUIDGenerator() {
      this.generatedUUID = UUID.randomUUID().toString();
    }

    public String getGeneratedUUID() {
      return generatedUUID;
    }

    public void setGeneratedUUID(String generatedUUID) {
      this.generatedUUID = generatedUUID;
    }
    
    
}
