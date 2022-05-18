package com.fandom.services;

import com.dropbox.core.DbxException;
import com.fandom.controller.WebSocketController;
import com.fandom.model.*;
import com.fandom.repository.MessageRepository;
import com.fandom.repository.UserRepository;
import com.fandom.util.DropboxUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Service
public class MessageServices {

    private MessageRepository mesRepo;
    private UserRepository userRepo;

    @Autowired
    public MessageServices(MessageRepository mesRepo, UserRepository userRepo){
        this.userRepo = userRepo;
        this.mesRepo = mesRepo;
    }

    public static SimpMessagingTemplate messageTemplate;

    public void sendMessage(Message mes){
        mes.setId();
        if(mes.getType().equals(MessageType.MESSAGE)){
            mes.setSenderState(MessageState.SENT);
            mes.setTimestamp(System.currentTimeMillis());
            mes.setMessageType(PostType.TEXT);
            if(WebSocketController.activeUser.contains(mes.getRecipient())){
                mes.setRecipientState(MessageState.RECEIVED);
                messageTemplate.convertAndSendToUser(mes.getRecipient(), "/msg", mes);
                messageTemplate.convertAndSendToUser(mes.getSender(), "/msg", mes);
            }else {
                mes.setRecipientState(MessageState.SENT);
                messageTemplate.convertAndSendToUser(mes.getSender(), "/msg", mes);
            }
        }
        User sender = userRepo.findUserByAccount(mes.getSender());
        User recipient = userRepo.findUserByAccount(mes.getRecipient());

        sender.addRecentChat(recipient.getAccount());
        recipient.addRecentChat(sender.getAccount());
        userRepo.save(sender);
        userRepo.save(recipient);
        mesRepo.save(mes);
    }

    public Map<String, Message> viewMessage(String chatId, String account, int page){

            Pageable paging = PageRequest.of(page, 15);
            Page<Message> messagePage = mesRepo.findByChatId(chatId, paging);
            Map<String, Message> map = new LinkedHashMap<>();
            List<Message> list = messagePage.getContent();
            for(int i = 0; i<=list.size() -1; i++){
                Message message = list.get(i);
                if(message.getSender().equals(account)){
                    if(!message.getSenderState().equals(MessageState.REMOVED)){
                        map.put(message.getId(), message);
                    }
                }else{
                    if(message.getRecipientState().equals(MessageState.SENT)){
                        message.setRecipientState(MessageState.SEEN);
                        mesRepo.save(message);
                        map.put(message.getId(), message);
                    }else {
                        map.put(message.getId(), message);
                    }
                };
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

    public void sendImageMessage(MultipartFile[] images, String sender, String recipient, String message, String chatId){
        ArrayList<Media> medias = new ArrayList<>();
        ArrayList<MultipartFile> arr = new ArrayList<>(Arrays.asList(images));
        for(int i = 0; i < arr.size(); i++){
            try {
                MultipartFile file = arr.get(i);
                Media m = DropboxUtils.uploadFile(file.getInputStream(), System.currentTimeMillis() + "");
                m.setDescription(m.getName());
                medias.add(m);
            } catch (DbxException | IOException e) {
                e.printStackTrace();
            }
        }

        Message mes = new Message(chatId,sender, recipient, message, medias.toArray(new Media[]{}));
        mes.setTimestamp(System.currentTimeMillis());
        mes.setId();
        if(WebSocketController.activeUser.contains(mes.getRecipient())){
            mes.setRecipientState(MessageState.RECEIVED);
            messageTemplate.convertAndSendToUser(mes.getRecipient(), "/msg", mes);
            messageTemplate.convertAndSendToUser(mes.getSender(), "/msg", mes);
        }else {
            mes.setRecipientState(MessageState.SENT);
            messageTemplate.convertAndSendToUser(mes.getSender(), "/msg", mes);
        }

        User senderUser = userRepo.findUserByAccount(mes.getSender());
        User recipientUser = userRepo.findUserByAccount(mes.getRecipient());
        senderUser.addRecentChat(recipientUser.getAccount());
        recipientUser.addRecentChat(senderUser.getAccount());
        userRepo.save(senderUser);
        userRepo.save(recipientUser);
        mesRepo.save(mes);
    }


    public Message getLastMessage(String chatId, String account){
        int page = 0;
        while(true){
            List<Message> message = mesRepo.findLastMessageByChatId(chatId, PageRequest.of(page, 15));
            for(Message mes: message){
                if(account.equals(mes.getRecipient()) && !mes.getRecipientState().equals(MessageState.REMOVED)){
                    return mes;
                }
                if(account.equals(mes.getSender()) && !mes.getSenderState().equals(MessageState.REMOVED)){
                    return mes;
                }
            }
            page = page +1;
        }

    }


}
