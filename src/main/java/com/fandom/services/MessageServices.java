package com.fandom.services;

import com.fandom.model.Message;
import com.fandom.model.MessageState;
import com.fandom.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServices {

    private MessageRepository mesRepo;

    @Autowired
    public MessageServices(MessageRepository mesRepo){
        this.mesRepo = mesRepo;
    }

    public static SimpMessagingTemplate messageTemplate;

    public void sendMessage(Message mes){

    }

    public List<Message> viewMessage(String sender, String recipient, int page){
            List<Message> messages = new ArrayList<>();
            Pageable paging = PageRequest.of(page, 15);

            String chatId = Message.generateChatId(sender, recipient);
            Page<Message> messagePage = mesRepo.findByChatId(chatId, paging);

            messages = messagePage.getContent();
            return messages;
    }

    public void deleteMessage(String id, String account){

        Message mes = mesRepo.findMessageById(id);

        if(mes.getSender().equals(account))
            mes.setSenderState(MessageState.REMOVED);
        else
            mes.setRecipientState(MessageState.REMOVED);
        mesRepo.save(mes);
    }

    //public List<Message> viewMessage(String account){}

}
