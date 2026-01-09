package com.example.useractivitytracker.exception;

import java.time.Instant;
import java.util.Map;

public class ApiError {

    private Instant timestamp;
    private int status;
    private String error;
    private Map<String, String> details;

    public ApiError(int status, String error, Map<String, String> details) {
        this.timestamp = Instant.now();
        this.status = status;
        this.error = error;
        this.details = details;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public Map<String, String> getDetails() {
        return details;
    }
}
