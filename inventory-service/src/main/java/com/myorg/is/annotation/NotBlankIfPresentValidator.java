package com.myorg.is.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class NotBlankIfPresentValidator implements ConstraintValidator<NotBlankIfPresent, String> {

  @Override
  public void initialize(NotBlankIfPresent constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    return Objects.isNull(value) || !value.isBlank();
  }
}
