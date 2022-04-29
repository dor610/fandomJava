package com.fandom.services;

import com.fandom.model.Message;
import com.fandom.model.MessageState;
import com.fandom.model.MessageType;
import com.fandom.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Message> viewMessage(String chatId, String account,int page){
            Pageable paging = PageRequest.of(page, 15);
            Page<Message> messagePage = mesRepo.findByChatId(chatId, paging);
            Map<String, Message> map = new LinkedHashMap();
            for(Message message: messagePage.getContent()){
                if(message.getSender().equals(account)){
                    if(!message.getSenderState().equals(MessageState.REMOVED))
                        map.put(message.getId(), message);
                }else map.put(message.getId(), message);
            }
            return map;
    }

    public Map<String, Message> viewNotification(String recipient, int page){
        Pageable pageable = PageRequest.of(page, 15);
        Page<Message> mesPage = mesRepo.findByRecipientAndType(recipient, MessageType.NOTIFICATION, pageable);
        Map<String, Message> map = new LinkedHashMap<>();
        for (Message noti: mesPage.getContent()){
            map.put(noti.getId(), noti);
        }

        return  map;
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
