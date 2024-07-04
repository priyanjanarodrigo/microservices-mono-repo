package com.myorg.os.exception;

import java.io.Serial;
import java.io.Serializable;
import lombok.ToString;

@ToString
public class BadRequestException extends RuntimeException implements Serializable {

  @Serial
  private static final long serialVersionUID = 2527978868716408518L;

  private final String message;

  public BadRequestException(String message) {
    super(message);
    this.message = message;
  }
}
