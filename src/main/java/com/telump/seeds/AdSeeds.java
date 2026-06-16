package com.telump.seeds;

public class AdSeeds {
    private final String title;
    private final String description;
    private final String targetUrl;

    public AdSeeds(String title, String description, String targetUrl) {
        this.title = title;
        this.description = description;
        this.targetUrl = targetUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTargetUrl() {
        return targetUrl;
    }
}
