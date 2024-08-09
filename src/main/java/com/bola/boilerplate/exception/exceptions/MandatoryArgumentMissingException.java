package com.bola.boilerplate.exception.exceptions;

/*
   Custom exception for the business layer
*/
public class MandatoryArgumentMissingException extends RuntimeException {
  /*
   No args constructors
  */
  public MandatoryArgumentMissingException() {
    super();
  }

  /*
   Message oriented constructor
  */
  public MandatoryArgumentMissingException(String message) {
    super(message);
  }

  /*
  All args constructor
   */
  public MandatoryArgumentMissingException(String message, Throwable cause) {
    super(message, cause);
  }

  /*
   Cause oriented constructor
  */
  public MandatoryArgumentMissingException(Throwable cause) {
    super(cause);
  }
}
