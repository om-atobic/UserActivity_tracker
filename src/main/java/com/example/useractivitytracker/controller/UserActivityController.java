package com.example.useractivitytracker.controller;

import com.example.useractivitytracker.dto.request.CreateActivityRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.useractivitytracker.service.UserActivityService;

@RestController
@RequestMapping("/activities")
public class UserActivityController {

    private final UserActivityService service;

    public UserActivityController(UserActivityService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void recordActivity(@Valid @RequestBody CreateActivityRequest request) {
        service.recordActivity(
                request.getUserId(),
                request.getActivityType(),
                request.getDetails()
        );
    }
}
