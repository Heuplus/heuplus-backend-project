package com.bola.boilerplate.exception.exceptions;

/*
   Exception for unauthorized action
*/
public class NotAllowedForTheActionException extends RuntimeException {
  /*
   No args constructors
  */
  public NotAllowedForTheActionException() {
    super();
  }

  /*
   Message oriented constructor
  */
  public NotAllowedForTheActionException(String message) {
    super(message);
  }

  /*
  All args constructor
   */
  public NotAllowedForTheActionException(String message, Throwable cause) {
    super(message, cause);
  }

  /*
   Cause oriented constructor
  */
  public NotAllowedForTheActionException(Throwable cause) {
    super(cause);
  }
}
