package com.myorg.is.exception;

import java.io.Serial;
import java.io.Serializable;
import lombok.ToString;

@ToString
public class GeneralClientDataException extends RuntimeException implements Serializable {

  @Serial
  private static final long serialVersionUID = 6727446126554560034L;

  private final String message;

  public GeneralClientDataException(String message) {
    super(message);
    this.message = message;
  }
}
