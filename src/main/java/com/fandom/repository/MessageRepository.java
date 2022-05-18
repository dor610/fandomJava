package com.fandom.repository;

import com.fandom.model.Message;
import com.fandom.model.MessageType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface MessageRepository extends MongoRepository<Message, String> {
    public Message findMessageById(String id);

    @Query(value = "{'chatId': ?0}", sort = "{'timestamp': -1}")
    public Page<Message> findByChatId(String chatId, Pageable pageable);

    public Page<Message> findByRecipientAndType(String recipient, MessageType type, Pageable pageable);

    @Query(value= "{'chatId': ?0}", sort = "{'timestamp': -1}")
    public List<Message> findLastMessageByChatId(String chatId, Pageable pageable);
}
