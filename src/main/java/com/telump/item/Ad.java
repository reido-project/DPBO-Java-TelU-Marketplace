package com.telump.item;

public class Ad extends CatalogItem {
    private String targetUrl;

    public Ad(String id, String title, String description, String targetUrl, String username) {
        super(id, title, description, username);
        this.targetUrl = targetUrl;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
