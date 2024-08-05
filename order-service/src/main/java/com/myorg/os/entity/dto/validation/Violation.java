package com.myorg.os.entity.dto.validation;

import lombok.Builder;

@Builder
public record Violation(String field, String message) {

}
