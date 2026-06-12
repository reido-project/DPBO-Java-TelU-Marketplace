package com.telump.catalog;
import com.telump.category.Category;

public class Item extends CatalogItem {
    private int price;
    private final String seller;
    private String imageName;
    private Category category;

    public Item(String id, String title, String description, int price, String seller,String imageName, Category category) {
        super(id, title, description);
        this.price = price;
        this.seller = seller;
        this.imageName = imageName;
        this.category = category;
    }
    public String getFormattedPrice() {
        return "Rp" + price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public String getSeller() {
            return seller;
    }
     public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
}
