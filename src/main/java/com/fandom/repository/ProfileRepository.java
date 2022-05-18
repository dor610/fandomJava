package com.fandom.repository;

import com.fandom.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {

    @Query(value = "{'id': ?0}", fields = "{'basicInfo': 1, 'links': 1}")
    public Profile getBasicProfile(String id);
}
