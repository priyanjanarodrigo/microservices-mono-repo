package com.myorg.is.annotation;

import static java.util.Objects.isNull;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankIfPresentValidator implements ConstraintValidator<NotBlankIfPresent, String> {

  /**
   * Initializes the constraint validator with the constraint annotation instance.
   *
   * @param notBlankIfPresentAnnotation NotBlankIfPresent annotation instance.
   */
  @Override
  public void initialize(NotBlankIfPresent notBlankIfPresentAnnotation) {
    ConstraintValidator.super.initialize(notBlankIfPresentAnnotation);
  }

  /**
   * If the value is null, then it is considered as valid and returns true (validation passes). If
   * the value is present, but it is either empty or blank, then it is considered as invalid and
   * returns false. (validation fails)
   *
   * @param value                      String value to be validated.
   * @param constraintValidatorContext ConstraintValidatorContext object.
   * @return boolean.
   */
  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    return isNull(value) || !value.isBlank();
  }
}
