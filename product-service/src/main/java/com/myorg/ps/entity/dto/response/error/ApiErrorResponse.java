package com.myorg.ps.entity.dto.response.error;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ApiErrorResponse(
    int statusCode,
    String path,
    String message,
    LocalDateTime timestamp) {

}