package com.fandom.repository;

import com.fandom.model.UserLog;
import com.fandom.model.UserState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserLogRepository extends MongoRepository<UserLog, String> {

    @Query(value = "{'userId': ?0, 'state': ?1}", sort = "{'timestamp': -1}")
    public Page<UserLog> getByUserIdAndState(String userId, UserState state, Pageable pageable);
}
