package com.fandom.model;

import com.fandom.model.infomation.Achievement;
import com.fandom.model.infomation.BasicInfo;
import com.fandom.model.infomation.LifeAndCareer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "profile")
public class Profile {

    @Id
    private String id;
    @Indexed
    private BasicInfo basicInfo;
    private LifeAndCareer[] lifeAndCareers;
    private Achievement achievement;
    private Map<String, Media> images;
}
