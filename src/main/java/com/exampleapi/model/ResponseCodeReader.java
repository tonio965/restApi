package com.exampleapi.model;

public class ResponseCodeReader {
  
  int code;
  
  public ResponseCodeReader(int code) {
    this.code=code;
  }
  
  public String returnResponseMessage() {
    switch(code) {
      case 1419:
        return "Message: MSISDN maximum balance reached or would be exceeded";
      case 1210:
        return "Message: Reload too high";
      case 1211:
        return "Message: Reload too low";
      case 1702:
        return "Message: Channel configuration not found";
      case 1113:
        return "Message: Invalid session state";
      case 1112:
        return "Message: Session forbidden";
      case 1105:
        return "Message: User Authorization failed";
      case 1101:
        return "Message: Mal parameter";
      case 1100:
        return "Message: Common system error";
      case 1001:
        return "Message: Success";
      default:
        return "Message: code message not added here, please add me :<";
    }

  }
  public int returnHttpCode() {
    switch(code) {
    case 1419:
      return 403;
    case 1210:
      return 403;
    case 1211:
      return 403;
    case 1702:
      return 500;
    case 1113:
      return 404;
    case 1112:
      return 403;
    case 1105:
      return 401;
    case 1101:
      return 400;
    case 1100:
      return 500;
    case 1001:
      return 201;
    default:
      return 997;
  }
    
  }

}
