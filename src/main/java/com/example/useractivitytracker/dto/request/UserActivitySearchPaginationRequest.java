package com.example.useractivitytracker.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UserActivitySearchPaginationRequest {
    @NotBlank(message = "userId is required")
    private String userId;


    private Integer limit;
    private ActivityCursor cursor;
    public String getUserId() {
        return userId;
    }
    public Integer getLimit() {
        return limit;
    }
    public ActivityCursor getCursor() {
        return cursor;
    }
}
