package com.fandom.repository;

import com.fandom.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    public User findUserByAccount(String account);
}
