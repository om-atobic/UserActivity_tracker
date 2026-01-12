package com.example.useractivitytracker.service;

import com.example.useractivitytracker.dto.request.ActivityCursor;
import com.example.useractivitytracker.dto.request.UserActivitySearchPaginationRequest;
import com.example.useractivitytracker.dto.request.UserActivitySearchRequest;
import com.example.useractivitytracker.dto.response.UserActivityPageresponse;
import com.example.useractivitytracker.dto.response.UserActivityResponse;
import com.example.useractivitytracker.repository.UserActivityRepository;
import org.springframework.stereotype.Service;
import com.example.useractivitytracker.model.UserActivity;
import com.example.useractivitytracker.repository.UserActivityRepository.PageResult;

import java.time.Instant;
import java.util.List;

@Service
public class UserActivityService {


    private static final int DEFAULT_LIMIT = 10;
    private static final int MAX_LIMIT = 50;
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
    public List<UserActivityResponse> getActivities(String userId,String ActivityType) {

        List<UserActivity> activities =
                repository.findByUserId(userId, ActivityType);


        return activities.stream()
                .map(activity -> new UserActivityResponse(
                        activity.getActivityType(),
                        activity.getDetails(),
                        activity.getActivityTimestamp()
                ))
                .toList();
    }
    public UserActivityPageresponse searchActivities(
            UserActivitySearchPaginationRequest request) {
        int limit=DEFAULT_LIMIT;
        if(request.getLimit() != null) {
             limit = Math.min(request.getLimit(), MAX_LIMIT);
        }


        PageResult<UserActivity> page =
                repository.queryByUserId(
                        request.getUserId(),
                        limit,
                        request.getCursor()
                );
        List<UserActivityResponse> responses =
                page.items().stream()
                        .map(this::toResponse)
                        .toList();

        ActivityCursor nextCursor = null;

        if (page.lastEvaluatedKey() != null) {
            nextCursor = new ActivityCursor(
                    page.lastEvaluatedKey().get("userId").s(),
                    page.lastEvaluatedKey().get("activityTimestamp").s()
            );
        }

        return new UserActivityPageresponse(responses, nextCursor);

    }
    private UserActivityResponse toResponse(UserActivity activity) {
        return new UserActivityResponse(
                activity.getActivityType(),
                activity.getDetails(),
                activity.getActivityTimestamp()
        );
    }


}
