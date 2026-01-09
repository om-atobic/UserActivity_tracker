package com.example.useractivitytracker.service;

import org.springframework.stereotype.Service;
import com.example.useractivitytracker.model.UserActivity;
import com.example.useractivitytracker.repository.UserActivityRepository;

import java.time.Instant;

@Service
public class UserActivityService {

    private final UserActivityRepository repository;

    public UserActivityService(UserActivityRepository repository) {
        this.repository = repository;
    }

    public void recordActivity(String userId, String activityType, String details) {
        UserActivity activity = new UserActivity();
        activity.setUserId(userId);
        activity.setActivityTimestamp(Instant.now().toString()); // ISO-8601
        activity.setActivityType(activityType);
        activity.setDetails(details);

        repository.save(activity);
    }
}
