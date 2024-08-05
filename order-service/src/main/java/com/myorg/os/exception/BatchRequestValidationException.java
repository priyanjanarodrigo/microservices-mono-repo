package com.myorg.os.exception;

import com.myorg.os.entity.dto.validation.Violation;
import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class BatchRequestValidationException extends RuntimeException implements Serializable {

  @Serial
  private static final long serialVersionUID = 7013403564450407021L;

  private final String message;

  private Map<Integer, List<Violation>> indexedBatchRequestViolations;

  public BatchRequestValidationException(String message,
      LinkedHashMap<Integer, List<Violation>> indexedBatchRequestViolations) {
    super(message);
    this.message = message;
    this.indexedBatchRequestViolations = indexedBatchRequestViolations;
  }
}
