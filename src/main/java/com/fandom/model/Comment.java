package com.fandom.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comment")
public class Comment {

    @Id
    private String id;

    @Indexed
    private String author;
    private String targetId;
    private String content;
    private Media[] image;
    private PostType type;
    private String timestamp;
    private PostState state;

    public Comment(){}

    public Comment(String id, String author, String targetId, String content, Media[] image, PostType type, String timestamp,
                   PostState state) {
        this.id = id;
        this.author = author;
        this.targetId = targetId;
        this.content = content;
        this.image = image;
        this.type = type;
        this.timestamp = timestamp;
        this.state = state;
    }

    public Comment(String author, String targetId, String content, Media[] image) {
        ObjectId id = new ObjectId();
        this.id = "comment_"+id.toString();
        this.author = author;
        this.targetId = targetId;
        this.content = content;
        this.image = image;
        this.type = PostType.IMAGE;
        this.timestamp = System.currentTimeMillis() + "";
        this.state = PostState.APPROVED;
    }

    public Comment(String author, String targetId, String content){
        ObjectId id = new ObjectId();
        this.id = "comment_"+id.toString();
        this.author = author;
        this.targetId = targetId;
        this.content = content;
        this.type = PostType.TEXT;
        this.timestamp = System.currentTimeMillis() +"";
        this.state = PostState.APPROVED;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Media[] getImage() {
        return image;
    }

    public void setImage(Media[] image) {
        this.image = image;
    }

    public PostType getType() {
        return type;
    }

    public void setType(PostType type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public PostState getState() {
        return state;
    }

    public void setState(PostState state) {
        this.state = state;
    }
}
