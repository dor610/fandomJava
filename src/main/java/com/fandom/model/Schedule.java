package com.fandom.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "schedule")
public class Schedule {

    @Id
    private String id;

    @Indexed
    private long timestamp;
    private String location;
    private String content;
    private PostState state;

    public Schedule() {
    }

    public Schedule(String id, long timestamp, String location, String content, PostState state) {
        this.id = id;
        this.timestamp = timestamp;
        this.location = location;
        this.content = content;
        this.state = state;
    }

    public Schedule(long timestamp, String location, String content) {
        ObjectId id = new ObjectId();
        this.id = "schedule_" + id.toString();
        this.timestamp = timestamp;
        this.location = location;
        this.content = content;
        this.state = PostState.APPROVED;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PostState getState() {
        return state;
    }

    public void setState(PostState state) {
        this.state = state;
    }
}
