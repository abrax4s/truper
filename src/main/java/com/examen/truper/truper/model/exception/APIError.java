package com.examen.truper.truper.model.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIError {
    private final String errorType;
    private final String errorCode;
    private final String errorDescription;
    private final String errorMessage;
    private final String url;
}
