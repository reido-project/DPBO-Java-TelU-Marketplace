package com.telump.item;

public abstract class CatalogItem {
    private final String id;
    private String title;
    private String description;
    private final String username;

    protected CatalogItem(String id, String title, String description, String username) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.username = username;
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

    public String getUsername() {
        return username;
    }
}
