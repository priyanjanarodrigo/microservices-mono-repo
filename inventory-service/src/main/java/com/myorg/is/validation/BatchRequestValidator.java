package com.myorg.is.validation;

import com.myorg.is.entity.dto.request.QuantityReduceRequest;
import com.myorg.is.entity.dto.validation.Violation;
import com.myorg.is.exception.InternalServerException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BatchRequestValidator {

  public static LinkedHashMap<Integer, List<Violation>> getQuantityReduceBatchRequestViolations(
      List<QuantityReduceRequest> quantityReduceRequests) {

    /* try with resources has been implemented to resolve "Ignore AutoClosable returned by this
       method" (buildDefaultValidatorFactory) warning */
    try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      Validator validator = validatorFactory.getValidator();
      LinkedHashMap<Integer, List<Violation>> indexedViolations = new LinkedHashMap<>();

      int index = 0;
      for (QuantityReduceRequest quantityReduceRequest : quantityReduceRequests) {
        Set<ConstraintViolation<QuantityReduceRequest>> constraintViolations = validator.validate(
            quantityReduceRequest, FullUpdateValidation.class);

        if (!constraintViolations.isEmpty()) {
          indexedViolations.put(index,
              constraintViolations.stream().map(v -> Violation.builder()
                      .field(v.getPropertyPath().toString())
                      .message(resolveViolationMessage(v.getMessage()))
                      .build())
                  .toList());
        }
        index++;
      }
      return indexedViolations;
    }
  }

  private static String resolveViolationMessage(String message) {
    try {
      ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
      return resourceBundle.getString(message.replace("{", "").replace("}", ""));
    } catch (Exception e) {
      throw new InternalServerException(
          "Unable to resolve validation error messages through the ResourceBundle.");
    }
  }
}
