package com.fandom.services;

import com.fandom.controller.WebSocketController;
import com.fandom.model.MessageState;
import com.fandom.model.Notification;
import com.fandom.model.NotificationType;
import com.fandom.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class NotificationServices {
    NotificationRepository nr;
    public static SimpMessagingTemplate messageTemplate;

    @Autowired
    public NotificationServices(NotificationRepository nr){
        this.nr = nr;
    }

    public Map<String, Notification> getNotifications(String recipient, int page){
        Page<Notification> p = nr.findByRecipient(recipient, PageRequest.of(page, 15));
        Map<String, Notification> map = new LinkedHashMap<>();
        for(Notification n: p.getContent()){
            if(!n.getState().equals(MessageState.REMOVED)){
                map.put(n.getId(), n);
            }
        }

        return map;
    }

    public void sendNotification(String recipient, String message, String note, NotificationType type){
        Notification n = new Notification(recipient, message, note, type);
        n.setId();
        if(WebSocketController.activeUser.contains(recipient)){
            messageTemplate.convertAndSendToUser(recipient, "/notification", n);
        }
        nr.save(n);
    }

    public void updateNotificationState(String id, MessageState state) {
        Optional<Notification> op = nr.findById(id);
        if (op.isPresent()) {
            Notification n = op.get();
            n.setState(state);
            nr.save(n);
        }
    }


}
