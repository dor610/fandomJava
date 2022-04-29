package com.fandom.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userLog")
public class UserLog {

    @Id
    private String id;

    @Indexed
    private String account;
    private UserState state;
    private long timestamp;
    private String note;

    public UserLog(){}

    public UserLog(String account, UserState state, String note) {
        ObjectId id = new ObjectId();
        this.id = "user_log_"+id.toString();
        this.account = account;
        this.state = state;
        this.timestamp = System.currentTimeMillis();
        this.note = note;
    }

    public UserLog(String id, String account, UserState state, long timestamp, String note) {
        this.id = id;
        this.account = account;
        this.state = state;
        this.timestamp = timestamp;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
