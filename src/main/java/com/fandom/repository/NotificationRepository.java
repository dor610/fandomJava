package com.fandom.repository;

import com.fandom.model.MessageState;
import com.fandom.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

    @Query(value = "{'recipient': ?0, 'state': ?1}", sort = "{'timestamp': -1}")
    public Page<Notification> findByRecipientAndState(String recipient, MessageState state, Pageable pageable);

    @Query(value = "{'recipient': ?0}", sort = "{'timestamp': -1}")
    public Page<Notification> findByRecipient(String recipient, Pageable pageable);
}
