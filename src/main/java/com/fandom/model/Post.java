package com.fandom.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "post")
public class Post {

    @Id
    private String id;

    @Indexed(unique = true)
    private String title;
    private String postContent;
    private String author;
    private String timestamp;
    private Media[] media;
    private PostType type;
    private PostState state;

    public Post(){};

    public Post(String id, String title, String postContent, String author, String timestamp, Media[] media, PostType type, PostState state) {
        this.id = id;
        this.title = title;
        this.postContent = postContent;
        this.author = author;
        this.timestamp = timestamp;
        this.media = media;
        this.type = type;
        this.state = state;
    }

    public Post(String title, String postContent, String author, Media[] media, PostType type) {
        this.title = title;
        this.postContent = postContent;
        this.author = author;
        this.timestamp = System.currentTimeMillis() + "";
        this.media = media;
        this.type = type;
        this.state = PostState.PENDING;
    }

    public Post(String title, String postContent, String author){
        this.title = title;
        this.postContent = postContent;
        this.author = author;
        this.state = PostState.PENDING;
        this.type = PostType.TEXT;
        this.timestamp = System.currentTimeMillis() + "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Media[] getMedia() {
        return media;
    }

    public void setMedia(Media[] media) {
        this.media = media;
    }

    public PostType getType() {
        return type;
    }

    public void setType(PostType type) {
        this.type = type;
    }

    public PostState getState() {
        return state;
    }

    public void setState(PostState state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
