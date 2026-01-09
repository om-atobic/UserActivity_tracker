package com.example.useractivitytracker.dto.response;

import java.time.Instant;

public class UserActivityResponse {

    private String activityType;
    private String details;
    private String activityTimestamp;

    public UserActivityResponse(String activityType, String details, String activityTimestamp) {
        this.activityType = activityType;
        this.details = details;
        this.activityTimestamp = activityTimestamp;
    }
    public String getActivityType() {
        return activityType;
    }

    public String getDetails() {
        return details;
    }

    public String getActivityTimestamp() {
        return activityTimestamp;
    }
}
