package com.example.useractivitytracker.dto.response;

import com.example.useractivitytracker.dto.request.ActivityCursor;

import java.util.List;

public class UserActivityPageresponse {
    private List<UserActivityResponse> items;
    private ActivityCursor nextCursor;

    public UserActivityPageresponse(List<UserActivityResponse> items, ActivityCursor nextCursor) {
        this.items = items;
        this.nextCursor = nextCursor;
    }
    public List<UserActivityResponse> getItems() {
        return items;
    }
    public ActivityCursor getNextCursor() {
        return nextCursor;
    }
}
