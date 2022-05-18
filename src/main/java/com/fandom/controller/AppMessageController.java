package com.fandom.controller;

import com.fandom.model.Message;
import com.fandom.services.MessageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class AppMessageController {
    private MessageServices ms;

    @Autowired
    public AppMessageController(MessageServices ms){
        this.ms = ms;
    }

    //xoa tin nhan
    @PostMapping("/messages/delete")
    public void deleteMessage(@RequestParam("messageId") String messageId, @RequestParam("account") String account) {
        ms.deleteMessage(messageId, account);
    }

    //kiem tra nguoi dung co online
    @GetMapping("/message/user/isOnline/{account}")
    public ResponseEntity<Boolean> isOnline(@PathVariable("account") String account){
        if(WebSocketController.activeUser.contains(account)){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }



    //lấy tin nhắn
    @GetMapping("/message/{account}/messages/{chatId}/{page}")
    public ResponseEntity<Map<String, Message>> ViewMessage(@PathVariable("chatId") String chatId,
                                                            @PathVariable("account") String account,
                                                            @PathVariable("page") String pageStr){
        Map<String, Message> map = ms.viewMessage(chatId,account, Integer.parseInt(pageStr));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //lấy notification
    @GetMapping("/message/{account}/notification/{page}")
    public ResponseEntity<Map<String, Message>> viewNotification(@PathVariable("account") String account, @PathVariable("page") String pageStr){
        Map<String, Message> map = ms.viewNotification(account, Integer.parseInt(pageStr));;
        if(map.size() == 0) return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/message/image")
    public ResponseEntity<String> sendImageMessage(@RequestParam("image")MultipartFile[] images, @RequestParam("sender") String sender,
                                                   @RequestParam("recipient") String recipient, @RequestParam("message") String message,
                                                   @RequestParam("chatId") String chatId){
        ms.sendImageMessage(images, sender, recipient, message, chatId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/message/{account}/last/{chatId}")
    public ResponseEntity<Message> getLastMessage(@PathVariable("account") String account, @PathVariable("chatId") String chatId){
        return new ResponseEntity<>(ms.getLastMessage(chatId, account), HttpStatus.OK);
    }
}
