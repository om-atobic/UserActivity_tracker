package com.example.useractivitytracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import software.amazon.awssdk.annotations.NotNull;

public class CreateActivityRequest {

    @NotBlank(message = "userId must not be blank")
    private String userId;

    @NotBlank(message = "activityType must not be null")
    private String activityType;
    private String details;

    public String getUserId() {
        return userId;
    }

    public String getActivityType() {
        return activityType;
    }

    public String getDetails() {
        return details;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}

