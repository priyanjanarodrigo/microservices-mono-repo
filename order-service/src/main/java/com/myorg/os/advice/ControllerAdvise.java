package com.myorg.os.advice;

import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.myorg.os.entity.dto.response.error.ApiErrorResponse;
import com.myorg.os.exception.BadRequestException;
import com.myorg.os.exception.BatchRequestValidationException;
import com.myorg.os.exception.InternalServerException;
import com.myorg.os.exception.OutOfStockException;
import com.myorg.os.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvise {

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler({MethodArgumentNotValidException.class})
  public Map<String, String> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException methodArgumentNotValidException) {

    return methodArgumentNotValidException
        .getBindingResult()
        .getFieldErrors()
        .stream()
        .collect(toMap(
            FieldError::getField,
            DefaultMessageSourceResolvable::getDefaultMessage,
            (firstKey, secondKey) -> secondKey,
            HashMap::new)
        );
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler({ConstraintViolationException.class})
  public Map<String, String> handleConstraintViolationException(
      ConstraintViolationException constraintViolationException) {

    return constraintViolationException
        .getConstraintViolations()
        .stream()
        .collect(toMap(
            constraintViolation -> {
              String propertyPath = constraintViolation.getPropertyPath().toString();
              return (propertyPath.contains("."))
                  ? propertyPath.substring(propertyPath.lastIndexOf(".") + 1) : propertyPath;
            },
            ConstraintViolation::getMessage
        ));
  }

  @ExceptionHandler(InternalServerException.class)
  public ResponseEntity<ApiErrorResponse> handleInternalServerException(
      InternalServerException internalServerException, HttpServletRequest httpServletRequest) {
    return this.createErrorResponse(internalServerException, httpServletRequest,
        INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(
      ResourceNotFoundException resourceNotFoundException, HttpServletRequest httpServletRequest) {
    return this.createErrorResponse(resourceNotFoundException, httpServletRequest, NOT_FOUND);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ApiErrorResponse> handleBadRequestException(
      RuntimeException runtimeException, HttpServletRequest httpServletRequest) {
    return this.createErrorResponse(runtimeException, httpServletRequest, BAD_REQUEST);
  }

  @ExceptionHandler(OutOfStockException.class)
  public ResponseEntity<ApiErrorResponse> handleConflictException(
      RuntimeException runtimeException, HttpServletRequest httpServletRequest) {
    return this.createErrorResponse(runtimeException, httpServletRequest, CONFLICT);
  }

  @ExceptionHandler(BatchRequestValidationException.class)
  public ResponseEntity<ApiErrorResponse> handleBatchRequestValidationException(
      BatchRequestValidationException exception, HttpServletRequest httpServletRequest) {

    ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
        .statusCode(BAD_REQUEST.value())
        .path(httpServletRequest.getRequestURI())
        .message(exception.getMessage())
        .timestamp(LocalDateTime.now())
        .indexedValidationErrors(exception.getIndexedBatchRequestViolations())
        .build();

    return new ResponseEntity<>(apiErrorResponse, BAD_REQUEST);
  }

  public ResponseEntity<ApiErrorResponse> createErrorResponse(RuntimeException exception,
      HttpServletRequest httpServletRequest, HttpStatus httpStatus) {
    return new ResponseEntity<>(ApiErrorResponse.builder()
        .statusCode(httpStatus.value())
        .path(httpServletRequest.getRequestURI())
        .message(exception.getMessage())
        .timestamp(LocalDateTime.now())
        .build(), httpStatus);
  }
}
