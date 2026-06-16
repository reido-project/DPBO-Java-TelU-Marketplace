package com.telump.seeds;

public class ItemSeeds {
    private final String title;
    private final String description;
    private final int price;
    private final int category;

    public ItemSeeds(String title, String description, int price, int category) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
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

    public int getCategory(){
        return category;
    }
}
