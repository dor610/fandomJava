package com.fandom.model.infomation;

import com.fandom.model.Media;

public class BasicInfo {

    private Media avatar;
    private String name;
    private String altName;
    private String dateOfBirth;
    private String placeOfBirth;
    private String occupation;
    private String genres;
    private String yearActive;
    private String spouse;
    private String children;

    public BasicInfo() {
    }

    public BasicInfo(String name, String altName, String dateOfBirth, String placeOfBirth, String occupation,
                     String genres, String yearActive, String spouse, Media avatar, String children){
        this.name = name;
        this.altName = altName;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.occupation = occupation;
        this.genres = genres;
        this.yearActive = yearActive;
        this.avatar = avatar;
        this.spouse = spouse;
        this.children = children;
    }

    public void setBasicInfo(String name, String altName, String dateOfBirth, String placeOfBirth, String occupation,
                             String genres, String yearActive, String spouse, String children){
        this.name = name;
        this.altName = altName;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.occupation = occupation;
        this.genres = genres;
        this.yearActive = yearActive;
        this.spouse = spouse;
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAltName() {
        return altName;
    }

    public void setAltName(String altName) {
        this.altName = altName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getYearActive() {
        return yearActive;
    }

    public void setYearActive(String yearActive) {
        this.yearActive = yearActive;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public Media getAvatar() {
        return avatar;
    }

    public void setAvatar(Media avatar) {
        this.avatar = avatar;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }
}
