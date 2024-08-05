package com.myorg.is.entity.dto.validation;

import lombok.Builder;

/**
 * Represents a violation of a validation rule.
 *
 * @param field   Field name that caused the violation.
 * @param message Message describing the violation.
 */
@Builder
public record Violation(String field, String message) {

}
