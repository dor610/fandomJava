package com.fandom.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "postLog")
public class PostLog {

    @Id
    private String id;

    @Indexed
    private String postId;
    private PostState state;
    private long timestamp;
    private String note;

    public PostLog(){}

    public PostLog(String id, String postId, PostState state, long timeStamp, String note) {
        this.id = id;
        this.postId = postId;
        this.state = state;
        this.timestamp = timeStamp;
        this.note = note;
    }

    public PostLog(String postId, PostState state, String note) {
        ObjectId id = new ObjectId();
        this.id = "post_log_"+ id.toString();
        this.postId = postId;
        this.state = state;
        this.timestamp = System.currentTimeMillis();
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public PostState getState() {
        return state;
    }

    public void setState(PostState state) {
        this.state = state;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timeStamp) {
        this.timestamp = timeStamp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
