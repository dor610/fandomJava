package com.fandom.model.infomation;

public class Achievement {
    private String brief;
    private String year;
    private String achievement;

    public Achievement() {
    }

    public Achievement(String brief, String year, String achievement) {
        this.brief = brief;
        this.year = year;
        this.achievement = achievement;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }
}
