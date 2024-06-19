package com.myorg.is.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotBlankIfPresentValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankIfPresent {

  String message() default "must not be empty or blank if present";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
