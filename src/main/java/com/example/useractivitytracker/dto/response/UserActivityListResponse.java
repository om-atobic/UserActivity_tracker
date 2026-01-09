package com.example.useractivitytracker.dto.response;

import java.util.List;

public class UserActivityListResponse {

    private String userId;
    private int total;
    private List<UserActivityResponse> activities;

    public UserActivityListResponse(
            String userId,
            int total,
            List<UserActivityResponse> activities) {
        this.userId = userId;
        this.total = total;
        this.activities = activities;
    }

    public String getUserId() {
        return userId;
    }

    public int getTotal() {
        return total;
    }

    public List<UserActivityResponse> getActivities() {
        return activities;
    }
}
