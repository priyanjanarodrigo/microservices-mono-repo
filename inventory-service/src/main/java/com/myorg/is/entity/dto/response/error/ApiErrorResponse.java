package com.myorg.is.entity.dto.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.myorg.is.entity.dto.validation.Violation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.Builder;

@Builder
public record ApiErrorResponse(
    int statusCode,
    String path,
    String message,
    LocalDateTime timestamp,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Map<Integer, List<Violation>> indexedValidationErrors) {

}

