package com.fandom.model;

import com.fandom.model.infomation.Achievement;
import com.fandom.model.infomation.BasicInfo;
import com.fandom.model.infomation.LifeAndCareer;
import com.fandom.model.infomation.Link;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "profile")
public class Profile {

    @Id
    private String id;
    @Indexed
    private List<Link> links;
    private BasicInfo basicInfo;
    private String lifeAndCareers;
    private String achievements;
    private String activities;

    public Profile(){}

    public Profile(String id , List<Link> links, BasicInfo basicInfo, String lifeAndCareers,
                   String achievements, String activities){
        this.id = id;
        this.links = links;
        this.basicInfo = basicInfo;
        this.lifeAndCareers = lifeAndCareers;
        this.achievements = achievements;
        this.activities = activities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public String getLifeAndCareers() {
        return lifeAndCareers;
    }

    public void setLifeAndCareers(String lifeAndCareers) {
        this.lifeAndCareers = lifeAndCareers;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }
}


