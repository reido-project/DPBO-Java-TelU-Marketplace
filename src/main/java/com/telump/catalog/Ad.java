package com.telump.catalog;
import com.telump.user.*;

public class Ad extends CatalogItem {
    private String targetUrl;
    private final User registrant;

    public Ad(String id, String title, String description, String targetUrl, User registrant) {
        super(id, title, description);
        this.targetUrl = targetUrl;
        this.registrant = registrant;
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
}
