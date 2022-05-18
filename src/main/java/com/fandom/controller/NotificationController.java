package com.fandom.controller;

import com.fandom.model.MessageState;
import com.fandom.model.Notification;
import com.fandom.services.NotificationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class NotificationController {

    NotificationServices ns;

    @Autowired
    public NotificationController(NotificationServices ns){
        this.ns = ns;
    }

    @GetMapping("/notification/get/{account}/{page}")
    public ResponseEntity<Map<String, Notification>> getNotifications(@PathVariable("account") String account, @PathVariable("page") String page){
        Map<String, Notification> map = ns.getNotifications(account, Integer.parseInt(page));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/notification/update/{id}")
    public ResponseEntity<String> updateNotificationState(@PathVariable("id") String id){
        ns.updateNotificationState(id, MessageState.SEEN);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/notification/delete/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable("id") String id){
        ns.updateNotificationState(id, MessageState.REMOVED);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
