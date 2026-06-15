package com.telump.catalog;

import com.telump.common.ItemType;

public class CatalogItem {
    private final String id;
    private String title;
    private String description;
    private final ItemType type;

    private final String imagePath;

    public CatalogItem(String id, String title, String description, ItemType type, String imagePath) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
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

    public ItemType getType(){
        return type;
    }

    public String getImagePath(){
        return imagePath;
    }
}
