package com.example.useractivitytracker.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UserActivitySearchRequest {

    @NotBlank(message = "userId is required")
    private String userId;

    private String activityType;
    public String getUserId() {
        return userId;
    }
    public String getActivityType() {
        return activityType;
    }
}
