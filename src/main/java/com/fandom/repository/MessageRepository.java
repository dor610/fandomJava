package com.fandom.repository;

import com.fandom.model.Message;
import com.fandom.model.MessageType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MessageRepository extends MongoRepository<Message, String> {
    public Message findMessageById(String id);

    public Page<Message> findByChatId(String chatId, Pageable pageable);

    public Page<Message> findByRecipientAndType(String recipient, MessageType type, Pageable pageable);
}
