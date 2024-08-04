package com.bola.boilerplate.exception.exceptions;

public class RoleChangeNotPossibleException extends RuntimeException {
  public RoleChangeNotPossibleException() {
    super();
  }

  public RoleChangeNotPossibleException(String message) {
    super(message);
  }

  public RoleChangeNotPossibleException(String message, Throwable cause) {
    super(message, cause);
  }

  public RoleChangeNotPossibleException(Throwable cause) {
    super(cause);
  }
}
