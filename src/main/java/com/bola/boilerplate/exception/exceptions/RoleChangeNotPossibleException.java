package com.bola.boilerplate.exception.exceptions;

/*
  Custom Exception implementation for business specific conflicts
 */
public class RoleChangeNotPossibleException extends RuntimeException {
  /*
    No args constructors
   */
  public RoleChangeNotPossibleException() {
    super();
  }

  /*
    Message oriented constructor
   */
  public RoleChangeNotPossibleException(String message) {
    super(message);
  }

  /*
  All args constructor
   */
  public RoleChangeNotPossibleException(String message, Throwable cause) {
    super(message, cause);
  }

  /*
    Cause oriented constructor
   */
  public RoleChangeNotPossibleException(Throwable cause) {
    super(cause);
  }
}
