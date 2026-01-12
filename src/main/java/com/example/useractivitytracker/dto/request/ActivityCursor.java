package com.example.useractivitytracker.dto.request;

public class ActivityCursor {
    private String userId;
    private String activityTimestamp;

    public ActivityCursor() {
    }

    public ActivityCursor(String userId, String activityTimestamp) {
        this.userId = userId;
        this.activityTimestamp = activityTimestamp;
    }

    public String getUserId() {
        return userId;
    }

    public String getActivityTimestamp() {

        return activityTimestamp;
    }

}
