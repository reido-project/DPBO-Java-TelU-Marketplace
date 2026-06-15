package com.telump.catalog;
import com.telump.user.*;

public class Ad {
    private final String id;
    private String title;
    private String description;
    private String targetUrl;
    private final User registrant;

    private final String imagePath;

    public Ad(String id, String title, String description, String targetUrl, User registrant, String imagePath) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.targetUrl = targetUrl;
        this.registrant = registrant;

        this.imagePath = imagePath;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public User getRegistrant() {
        return registrant;
    }

    public String getImagePath(){
        return imagePath;
    }
}
