package com.telump.catalog;

import java.util.Objects;

import com.telump.common.ItemType;

public class CatalogItem {
    private final String id;
    private String title;
    private String description;
    private final ItemType type;

    private final String imagePath;
    private final String generationToken;

    public CatalogItem(String id, String title, String description, ItemType type, String imagePath, String generationToken) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.imagePath = imagePath;
        this.generationToken = generationToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatalogItem that = (CatalogItem) o;
        return Objects.equals(id, that.id) && 
            type == that.type && 
            Objects.equals(generationToken, that.generationToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, generationToken);
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

    public String getGenerationToken(){
        return generationToken;
    }
}
