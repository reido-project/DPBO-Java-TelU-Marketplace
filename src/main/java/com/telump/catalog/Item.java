package com.telump.catalog;
import com.telump.category.Category;
import com.telump.common.Util;
import com.telump.user.User;

public class Item {
    private final String id;

    private String title;
    private String description;
    private int price;
    private final User seller;
    private Category category;

    private final String imagePath;

    public Item(String id, String title, String description, int price, User seller, Category category, String imagePath) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.seller = seller;
        this.category = category;
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

    public int getPrice() {
        return price;
    }

    public String getFormattedPrice(){
        return Util.formatPrice(price);
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public User getSeller() {
        return seller;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImagePath() {
        return imagePath;
    }
}
