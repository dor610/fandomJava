package com.fandom.repository;

import com.fandom.model.PostLog;
import com.fandom.model.PostState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PostLogRepository extends MongoRepository<PostLog, String> {

    @Query(value = "{'postId': ?0, 'state': ?1}", sort = "{'timestamp': -1}")
    public Page<PostLog> getByPostIdAndState(String postId, PostState state, Pageable pageable);
}
