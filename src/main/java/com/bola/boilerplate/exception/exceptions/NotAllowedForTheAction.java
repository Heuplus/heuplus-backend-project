package com.bola.boilerplate.exception.exceptions;

/*
    Exception for unauthorized action
 */
public class NotAllowedForTheAction extends RuntimeException {
    /*
   No args constructors
  */
    public NotAllowedForTheAction() {
        super();
    }

    /*
     Message oriented constructor
    */
    public NotAllowedForTheAction(String message) {
        super(message);
    }

    /*
    All args constructor
     */
    public NotAllowedForTheAction(String message, Throwable cause) {
        super(message, cause);
    }

    /*
     Cause oriented constructor
    */
    public NotAllowedForTheAction(Throwable cause) {
        super(cause);
    }
}
