package com.fandom.model;

public class Media {
    private String url;
    private String name;
    private String path;
    private String description;

    public Media(){}

    public Media(String url, String name, String path, String description){
        this.name = name;
        this.path = path;
        this.url = url;
        this.description = description;
    }
    public Media(String url, String name, String path){
        this.name = name;
        this.path = path;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
