package com.fandom.repository;

import com.fandom.model.User;
import com.fandom.model.UserState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {

    public User findUserByAccount(String account);

    @Query(value = "{'status': ?0}", fields = "{'account': 1, 'userName': 1, 'avatar': 1, 'createdDate': 1}")
    public Page<User> findUserbyStatus(UserState status, Pageable pageable);


    public int countAllByStatus(UserState state);
}
