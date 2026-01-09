package com.example.useractivitytracker.repository;

import com.example.useractivitytracker.model.UserActivity;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.List;

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

    public List<UserActivity> findByUserId(String userId,String activityType) {
        if (activityType != null && !activityType.isEmpty()) {
            //if activityType is not null or empty then find by userId and activityType
            return findByUserIdAndActivityType(userId,activityType);
        }
            QueryConditional queryConditional =
                    QueryConditional.keyEqualTo(
                            Key.builder()
                                    .partitionValue(userId)
                                    .build()
                    );
            return userActivityTable
                    .query(r -> r.queryConditional(queryConditional)
                            .scanIndexForward(false)) // latest first
                    .items()
                    .stream()
                    .toList();


    }
    public List<UserActivity> findByUserIdAndActivityType(String userId,String activityType ) {

        DynamoDbIndex<UserActivity> index =
                userActivityTable.index("userId_to_Atype");

        QueryConditional queryConditional =
                QueryConditional.keyEqualTo(
                        Key.builder()
                                .partitionValue(userId)
                                .sortValue(activityType)
                                .build()
                );

        return index
                .query(r -> r.queryConditional(queryConditional))
                .stream()
                .flatMap(page -> page.items().stream())
                .toList();

    }


}
