package com.fandom.controller;

import com.fandom.model.Message;
import com.fandom.services.MessageServices;
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

    @Autowired
    public void setMessageServices(MessageServices messageServices){
        this.messageServices = messageServices;
    }

    public WebSocketController(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
        MessageServices.messageTemplate = simpMessagingTemplate;
        activeUser = new HashSet<>();
    }

    @ResponseBody
    @GetMapping("/message")
    public ResponseEntity<List<Message>> viewMessage(@RequestParam("sender") String sender, @RequestParam("page") int page,
                                                     @RequestParam("recipient") String recipient){
        //List<Message> messages =

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // add an user to connected user list
    @MessageMapping("/register")
    @SendToUser("/queue/newMember")
    public Set<String> registerUser(String webChatUsername, SimpMessageHeaderAccessor headerAccessor){
        System.out.println("webSocketController: new connect "+ webChatUsername);
        if(!activeUser.contains(webChatUsername)) {
            // Add username in web socket session
            headerAccessor.getSessionAttributes().put("username", webChatUsername);
            activeUser.add(webChatUsername);
            simpMessagingTemplate.convertAndSend("/topic/newMember", webChatUsername);
            return activeUser;
        } else {
            return activeUser;
        }
    }

    // remove an user from connected user list
    @MessageMapping("/unregister")
    @SendTo("/topic/disconnectedUser")
    public String unregisterUser(String webChatUsername){
        activeUser.remove(webChatUsername);
        return webChatUsername;
    }

    // send message to a specific user
    @MessageMapping("/message")
    public void sendMessage(Message message){
        messageServices.sendMessage(message);
        //simpMessagingTemplate.convertAndSendToUser(message.getRecipient(), "/msg", message);
    }

    //xoá tin nhắn
    @PostMapping("/messages/delete")
    public void deleteMessage(@RequestParam("messageId") String messageId, @RequestParam("account") String account) {
        messageServices.deleteMessage(messageId, account);
    }
    @PostMapping("/app/messages/delete")
    public void appDeleteMessage(@RequestHeader("messageId") String messageId, @RequestHeader("email") String email) {
        messageServices.deleteMessage(messageId, email);
    }


    @GetMapping("/app/{account}/messages/{chatId}/{page}")
    public ResponseEntity<Map<String, Message>> appViewPrivateMessage(@PathVariable("chatId") String chatId,
                                                                      @PathVariable("account") String account,
                                                                      @PathVariable("page") String pageStr){
        Map<String, Message> map = messageServices.viewMessage(chatId,account, Integer.parseInt(pageStr));
        if (map.size() == 0) return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/app/{account}/notification/{page}")
    public ResponseEntity<Map<String, Message>> appViewNotification(@PathVariable("account") String account, @PathVariable("page") String pageStr){
        Map<String, Message> map = messageServices.viewNotification(account, Integer.parseInt(pageStr));;
        if(map.size() == 0) return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
