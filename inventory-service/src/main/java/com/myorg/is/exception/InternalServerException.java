package com.myorg.is.exception;

import java.io.Serial;
import java.io.Serializable;
import lombok.ToString;

@ToString
public class InternalServerException extends RuntimeException implements Serializable {

  @Serial
  private static final long serialVersionUID = 2543898730626477521L;

  private final String message;

  public InternalServerException(String message) {
    super(message);
    this.message = message;
  }
}
