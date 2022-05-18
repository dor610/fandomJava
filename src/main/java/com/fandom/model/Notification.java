package com.fandom.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notification")
public class Notification {

    @Id
    private String id;
    @Indexed
    private String recipient;
    private String message;
    private MessageState state;
    private String note;
    private NotificationType type;
    private long timestamp;

    public Notification() {
    }

    public Notification(String id, String recipient, String message, MessageState state, String note, NotificationType type, long timestamp) {
        this.id = id;
        this.recipient = recipient;
        this.message = message;
        this.state = state;
        this.note = note;
        this.type = type;
        this.timestamp = timestamp;
    }

    public Notification(String recipient, String message, String note, NotificationType type){
        this.recipient = recipient;
        this.message = message;
        this.note = note;
        this.type = type;
        this.timestamp = System.currentTimeMillis();
        this.state = MessageState.SENT;
    }

    public void setId(){
        ObjectId objectId = new ObjectId();
        this.id = "notification_"+objectId.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageState getState() {
        return state;
    }

    public void setState(MessageState state) {
        this.state = state;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
