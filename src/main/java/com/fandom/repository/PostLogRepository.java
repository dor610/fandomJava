package com.fandom.repository;

import com.fandom.model.PostLog;
import com.fandom.model.PostState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PostLogRepository extends MongoRepository<PostLog, String> {

    @Query(value = "{'postId': ?0, 'state': ?1}", sort = "{'timestamp': -1}")
    public List<PostLog> getByPostIdAndState(String postId, PostState state);

    @Query(value = "{'postId': ?0}", sort = "{'timestamp': -1}")
    public List<PostLog> getByPostId(String postId);

    @Query(value = "{'timestamp': {$gte: ?0}}" , sort = "{'timestamp': 1}")
    public List<PostLog> getUpcoming(long timestamp);

    public List<PostLog> findByTimestampBetween(long start, long end);



    //Đếm số bài theo postId và state
    public int countByPostIdAndState(String postId, PostState state);

    //có tồn tại theo postId và state
    public boolean existsPostLogByPostIdAndState(String postId, PostState state);
}
