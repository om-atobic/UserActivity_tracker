package com.example.useractivitytracker.repository;

import com.example.useractivitytracker.dto.request.ActivityCursor;
import com.example.useractivitytracker.model.UserActivity;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;

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

        /*
        QueryConditional queryConditional =
                QueryConditional.keyEqualTo(
                        Key.builder()
                                .partitionValue(userId)
                                .sortValue(activityType)
                                .build()
                );
*/
        QueryConditional queryConditional =
                QueryConditional.sortBeginsWith(
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
    public record PageResult<T>(
            List<T> items,
            Map<String, AttributeValue> lastEvaluatedKey
    ) {}

    public PageResult<UserActivity> queryByUserId(
            String userId,
            int limit,
            ActivityCursor cursor) {

        QueryConditional condition =
                QueryConditional.keyEqualTo(
                        Key.builder()
                                .partitionValue(userId)
                                .build()
                );

        QueryEnhancedRequest.Builder requestBuilder =
                QueryEnhancedRequest.builder()
                        .queryConditional(condition)
                        .limit(limit)
                        .scanIndexForward(false);

        if (cursor != null) {
            requestBuilder.exclusiveStartKey(
                    Map.of(
                            "userId", AttributeValue.fromS(cursor.getUserId()),
                            "activityTimestamp", AttributeValue.fromS(cursor.getActivityTimestamp())
                    )
            );
        }

        PageIterable<UserActivity> pages =
                userActivityTable.query(requestBuilder.build());

        Page<UserActivity> firstPage = pages.iterator().next();

        return new PageResult<>(
                firstPage.items(),
                firstPage.lastEvaluatedKey()
        );
    }



}
