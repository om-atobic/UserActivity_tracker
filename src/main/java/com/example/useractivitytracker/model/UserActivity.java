package com.example.useractivitytracker.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.Instant;

@DynamoDbBean
public class UserActivity {

    private String userId;
    private String activityTimestamp;
    private String activityType;
    private String details;


    @DynamoDbPartitionKey
    @DynamoDbSecondaryPartitionKey(indexNames = "userId_to_Atype")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDbSortKey
    public String getActivityTimestamp() {
        return activityTimestamp;
    }

    public void setUserIdGsi(String userId) {
        this.userId = userId;
    }

    @DynamoDbSecondarySortKey(indexNames = "userId_to_Atype")
    public String getActivityType() {
        return activityType;
    }

    public void setActivityTimestamp(String activityTimestamp) {
        this.activityTimestamp = activityTimestamp;
    }


    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Instant getTimestamp() {
        return Instant.parse(activityTimestamp);
    }

    public String getMetadata() {
        return details;
    }

}
