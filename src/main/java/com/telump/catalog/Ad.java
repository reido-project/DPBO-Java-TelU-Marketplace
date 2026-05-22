package com.telump.catalog;

public class Ad extends CatalogItem {
    private String targetUrl;
    private final String registrant;

    public Ad(String id, String title, String description, String targetUrl, String registrant) {
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

    public String getRegistrant() {
        return registrant;
    }
}
