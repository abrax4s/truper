package com.examen.truper.truper.common.exception.handler;

import com.examen.truper.truper.common.exception.TruperPurchaseException;
import com.examen.truper.truper.model.exception.APIError;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Provider
@Component
@Slf4j
@RestControllerAdvice
public class TruperPurchaseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TruperPurchaseException.class)
    public ResponseEntity<?> toResponse(TruperPurchaseException e) {
        int httpStatus = e.getErrorCode();
        String errorMessage = e.getErrorMessage();
        log.error(String.format("An error was raised during execution with status %s and details %s", httpStatus, errorMessage),e);
        return handleExceptionInternal(e, APIError.builder()
                .errorCode(String.valueOf(httpStatus))
                .errorMessage(errorMessage)
                .build(), new HttpHeaders(), HttpStatusCode.valueOf(httpStatus), null);
    }
}
