package com.fandom.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "message")
public class Message {
    @Id
    private String id;

    @Indexed(unique = true)
    private String chatId;
    private String sender;
    private String recipient;
    private long timestamp;
    private String message;
    private Media[] images;
    private MessageState senderState;
    private MessageState recipientState;
    private PostType messageType;
    private MessageType type;

    public Message(){}

    public Message(String id, String chatId, String sender, String recipient, Media[] images, long timestamp, String message,
                   MessageState senderState, MessageState recipientState, PostType messageType, MessageType type) {
        this.id = id;
        this.chatId = chatId;
        this.sender = sender;
        this.images = images;
        this.recipient = recipient;
        this.timestamp = timestamp;
        this.message = message;
        this.senderState = senderState;
        this.recipientState = recipientState;
        this.messageType = messageType;
        this.type = type;
    }

    public Message(String chatId, String sender, String recipient, String message) {
        ObjectId id = new ObjectId();
        this.id = "message_"+id.toString();
        this.chatId = chatId;
        this.sender = sender;
        this.recipient = recipient;
        this.timestamp = System.currentTimeMillis();
        this.message = message;
        this.messageType = PostType.TEXT;
        this.senderState = MessageState.SENT;
        this.recipientState = MessageState.RECEIVED;
        this.type = MessageType.MESSAGE;
    }

    public String toString(){
        return  chatId+ "   " + sender+"   "+ recipient +"  "+ message;
    }

    public Message(String chatId, String sender, String recipient, String message, Media[] images){
        ObjectId id = new ObjectId();
        this.id = "message_"+id.toString();
        this.chatId = chatId;
        this.sender = sender;
        this.recipient = recipient;
        this.timestamp = System.currentTimeMillis();
        this.message = message;
        this.messageType = PostType.IMAGE;
        this.senderState = MessageState.SENT;
        this.recipientState = MessageState.RECEIVED;
        this.type = MessageType.MESSAGE;
        this.images = images;
    }

    //constructor for notification

    public static String generateChatId(String sender, String recipient){
        String chatId = "";
        if(sender.compareTo(recipient) > 0)
            chatId = sender+recipient;
        else chatId = recipient+sender;

        return chatId;
    }

    public void setId(){
        ObjectId id = new ObjectId();
        this.id = "message_"+id.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageState getSenderState() {
        return senderState;
    }

    public void setSenderState(MessageState senderState) {
        this.senderState = senderState;
    }

    public MessageState getRecipientState() {
        return recipientState;
    }

    public void setRecipientState(MessageState recipientState) {
        this.recipientState = recipientState;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Media[] getImages() {
        return images;
    }

    public void setImages(Media[] images) {
        this.images = images;
    }

    public PostType getMessageType() {
        return messageType;
    }

    public void setMessageType(PostType messageType) {
        this.messageType = messageType;
    }
}