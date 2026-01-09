package com.example.useractivitytracker.repository;

import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import com.example.useractivitytracker.model.UserActivity;

@Repository
public class UserActivityRepository {

    private final DynamoDbTable<UserActivity> userActivityTable;

    public UserActivityRepository(DynamoDbEnhancedClient enhancedClient) {
        this.userActivityTable = enhancedClient.table(
                "user_activity",
                TableSchema.fromBean(UserActivity.class)
        );
    }

    public void save(UserActivity activity) {
        userActivityTable.putItem(activity);
    }
}
