package com.example.useractivitytracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        fieldErrors.put(error.getField(), error.getDefaultMessage())
                );

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                fieldErrors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiError);
    }


    @ExceptionHandler(DynamoDbException.class)
    public ResponseEntity<ApiError> handleDynamoDbException(DynamoDbException ex) {

        Map<String, String> details = new HashMap<>();

        if (ex.getMessage().toLowerCase().contains("expired")) {
            details.put("message", "AWS credentials expired. Please refresh credentials.");

            ApiError error = new ApiError(
                    401,
                    "AWS Authentication Error",
                    details
            );

            return ResponseEntity.status(401).body(error);
        }

        details.put("message", ex.getMessage());

        ApiError error = new ApiError(
                500,
                "DynamoDB Error",
                details
        );

        return ResponseEntity.status(500).body(error);
    }

}
