package com.fandom.controller;

import com.fandom.model.Message;
import com.fandom.model.MessageType;
import com.fandom.model.Notification;
import com.fandom.services.MessageServices;
import com.fandom.services.NotificationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
public class WebSocketController {

    public static SimpMessagingTemplate simpMessagingTemplate;
    public static Set<String> activeUser;
    private MessageServices messageServices;
    private NotificationServices notificationServices;

    @Autowired
    public void setMessageServices(MessageServices messageServices){
        this.messageServices = messageServices;
    }

    @Autowired
    public void setNotificationServices(NotificationServices notificationServices){
        this.notificationServices = notificationServices;
    }

    public WebSocketController(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
        MessageServices.messageTemplate = simpMessagingTemplate;
        NotificationServices.messageTemplate = simpMessagingTemplate;
        activeUser = new HashSet<>();
    }

    //----------------------------------------------------------------------------------------------------------------//

    // add an user to connected user list
    @MessageMapping("/register")
    //Định nghĩa cái điểm đến của tin nhắn. để khi return sẽ gửi về đường dẫn đó
    @SendToUser("/queue/newMember")
    public Set<String> registerUser(String webChatUsername, SimpMessageHeaderAccessor headerAccessor){
        System.out.println("webSocketController: new connect "+ webChatUsername);
        if(!activeUser.contains(webChatUsername)) {
            // Add username to web socket session
            headerAccessor.getSessionAttributes().put("username", webChatUsername);
            activeUser.add(webChatUsername);
            simpMessagingTemplate.convertAndSend("/topic/newMember", webChatUsername);
            return activeUser;
        } else {
            return activeUser;
        }
    }



    //remove an user from connected user list
    @MessageMapping("/unregister")
    //Định nghĩa cái điểm đến của tin nhắn. để khi return sẽ gửi về đường dẫn đó
    @SendTo("/topic/disconnectedUser")
    public String unregisterUser(String webChatUsername){
        activeUser.remove(webChatUsername);
        return webChatUsername;
    }

    //send message to a specific user
    //Không định nghĩa điểm đến vì cái phương thức sendMessage đã khai báo điểm đến rồi
    //      "/app/message"
    @MessageMapping("/message")
    public void sendMessage(Message message){
        message.setType(MessageType.MESSAGE);
        messageServices.sendMessage(message);
        //System.out.println(message.toString());
        //simpMessagingTemplate.convertAndSendToUser(message.getRecipient(), "/msg", message);
    }
    //      "/app/notification"
    @MessageMapping("/notification")
    public void sendNotification(Message message){
        message.setType(MessageType.NOTIFICATION);
    }



}
