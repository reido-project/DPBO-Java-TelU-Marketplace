package com.telump.seeds;

public class ItemSeeds {
    private final String title;
    private final String description;
    private final int price;

    public ItemSeeds(String title, String description, int price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}
