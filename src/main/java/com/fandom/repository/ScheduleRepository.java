package com.fandom.repository;

import com.fandom.model.PostState;
import com.fandom.model.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ScheduleRepository extends MongoRepository<Schedule, String> {

    @Query(sort = "{'timestamp': -1}")
    public Page<Schedule> getSchedulesByState(PostState state, Pageable pageable);

    @Query(value = "{'timestamp': {$gte: ?0}}" , sort = "{'timestamp': 1}")
    public List<Schedule> getUpcoming(long timestamp);

    @Query(value = "{'timestamp': {$lt: ?0}}", sort = "{'timestamp': -1}")
    public List<Schedule> getFinished(long timestamp);

}
