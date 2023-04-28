package com.myorg.os.entity.dto.response.error;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class ApiErrorResponse {

  private int statusCode;

  private String path;

  private String message;

  private LocalDateTime timestamp;
}

