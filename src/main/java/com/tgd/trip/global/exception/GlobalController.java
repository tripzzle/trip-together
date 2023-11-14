package com.tgd.trip.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalController {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode().getHttpStatus(), e.getErrorCode().getMessage());
        return ResponseEntity.status(errorResponse.status()).body(errorResponse);
    }
}
