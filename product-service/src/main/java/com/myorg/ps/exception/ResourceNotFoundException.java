package com.myorg.ps.exception;

import java.io.Serial;
import java.io.Serializable;
import lombok.ToString;

@ToString
public class ResourceNotFoundException extends RuntimeException implements Serializable {

  @Serial
  private static final long serialVersionUID = 2543898730626477521L;

  private final String message;

  public ResourceNotFoundException(String message) {
    super(message);
    this.message = message;
  }
}
