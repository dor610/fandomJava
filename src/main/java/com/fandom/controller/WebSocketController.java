package com.fandom.controller;

import com.fandom.model.Message;
import com.fandom.services.MessageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@CrossOrigin(origins = "*")
public class WebSocketController {

    public static SimpMessagingTemplate simpMessagingTemplate;
    public static Set<String> activeUser;

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

}
