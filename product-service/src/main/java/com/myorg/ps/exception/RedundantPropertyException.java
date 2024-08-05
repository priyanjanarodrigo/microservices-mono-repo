package com.myorg.ps.exception;

import java.io.Serial;
import java.io.Serializable;
import lombok.ToString;

@ToString
public class  RedundantPropertyException extends RuntimeException implements Serializable {

  @Serial
  private static final long serialVersionUID = 5183860466340956294L;

  private final String message;

  public RedundantPropertyException(String message) {
    super(message);
    this.message = message;
  }
}
