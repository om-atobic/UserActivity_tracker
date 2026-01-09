package com.example.useractivitytracker.controller;

import com.example.useractivitytracker.dto.request.CreateActivityRequest;
import com.example.useractivitytracker.dto.request.UserActivitySearchRequest;
import com.example.useractivitytracker.dto.response.UserActivityListResponse;
import com.example.useractivitytracker.dto.response.UserActivityResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.useractivitytracker.service.UserActivityService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserActivityController {

    private final UserActivityService service;

    public UserActivityController(UserActivityService service) {
        this.service = service;
    }

    @PostMapping("/activities")
    @ResponseStatus(HttpStatus.CREATED)
    public void recordActivity(@Valid @RequestBody CreateActivityRequest request) {
        service.recordActivity(
                request.getUserId(),
                request.getActivityType(),
                request.getDetails()
        );
    }

    @PostMapping("/search")
    public ResponseEntity<UserActivityListResponse> getUserActivities(
            @Valid @RequestBody UserActivitySearchRequest request) {

        List<UserActivityResponse> activities =
                service.getActivities(request.getUserId(),request.getActivityType());

        UserActivityListResponse response =
                new UserActivityListResponse(
                        request.getUserId(),
                        activities.size(),
                        activities
                );

        return ResponseEntity.ok(response);
    }
}

