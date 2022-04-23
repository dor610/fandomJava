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
    private String timestamp;
    private String location;
    private String content;

    public Schedule() {
    }

    public Schedule(String id, String timestamp, String location, String content) {
        this.id = id;
        this.timestamp = timestamp;
        this.location = location;
        this.content = content;
    }

    public Schedule(String timestamp, String location, String content) {
        ObjectId id = new ObjectId();
        this.id = "schedule_" + id.toString();
        this.timestamp = timestamp;
        this.location = location;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
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
}
