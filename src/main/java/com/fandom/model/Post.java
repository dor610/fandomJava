package com.fandom.model;

import org.bson.types.ObjectId;
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
    private Media[] images;
    private Media video;
    private PostType type;
    private PostState state;

    public Post(){};

    public Post(String id, String title, String postContent, String author, String timestamp, Media[] images, Media video, PostType type, PostState state) {
        this.id = id;
        this.title = title;
        this.postContent = postContent;
        this.author = author;
        this.timestamp = timestamp;
        this.images = images;
        this.video = video;
        this.type = type;
        this.state = state;
    }

    //images and video post
    public Post(String title, String postContent, String author, Media[] images, Media video) {
        ObjectId id = new ObjectId();
        this.id = "post_"+id.toString();
        this.title = title;
        this.postContent = postContent;
        this.author = author;
        this.timestamp = System.currentTimeMillis() + "";
        this.images = images;
        this.video = video;
        this.type = PostType.IMAGE_VIDEO;
        this.state = PostState.PENDING;
    }

    //video only post
    public Post(String title, String postContent, String author, Media video) {
        ObjectId id = new ObjectId();
        this.id = "post_"+id.toString();
        this.title = title;
        this.postContent = postContent;
        this.author = author;
        this.timestamp = System.currentTimeMillis() + "";
        this.video = video;
        this.type = PostType.VIDEO;
        this.state = PostState.PENDING;
    }

    //image only post
    public Post(String title, String postContent, String author, Media[] images) {
        ObjectId id = new ObjectId();
        this.id = "post_"+id.toString();
        this.title = title;
        this.postContent = postContent;
        this.author = author;
        this.timestamp = System.currentTimeMillis() + "";
        this.images = images;
        this.type = PostType.IMAGE;
        this.state = PostState.PENDING;
    }

    //text only post
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

    public Media[] getImages() {
        return images;
    }

    public void setImages(Media[] images) {
        this.images = images;
    }

    public Media getVideo() {
        return video;
    }

    public void setVideo(Media video) {
        this.video = video;
    }
}
