package com.myorg.is.exception;

import java.io.Serial;
import java.io.Serializable;
import lombok.ToString;

@ToString
public class EmptyPatchRequestException extends RuntimeException implements Serializable {

  @Serial
  private static final long serialVersionUID = 4127234782095856216L;

  private final String message;

  public EmptyPatchRequestException(String message) {
    super(message);
    this.message = message;
  }
}
