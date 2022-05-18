package com.fandom.model.infomation;

import com.fandom.model.Media;

import java.util.Map;

public class LifeAndCareer {

    private String title;
    private String content;
    private Map<String, Media> images;

    public LifeAndCareer() {
    }

    public LifeAndCareer (String title, String content, Map<String, Media> images){
        this.title = title;
        this.content = content;
        this.images = images;
    }

    public LifeAndCareer (String title, String content){
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, Media> getImages() {
        return images;
    }

    public void setImages(Map<String, Media> images) {
        this.images = images;
    }
}
