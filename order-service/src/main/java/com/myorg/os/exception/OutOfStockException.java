package com.myorg.os.exception;

import java.io.Serial;
import java.io.Serializable;
import lombok.ToString;

@ToString
public class OutOfStockException extends RuntimeException implements Serializable {

  @Serial
  private static final long serialVersionUID = -6849210951114133569L;

  private final String message;

  public OutOfStockException(String message) {
    super(message);
    this.message = message;
  }
}
