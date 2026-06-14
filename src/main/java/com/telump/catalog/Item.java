package com.telump.catalog;
import com.telump.category.Category;
import com.telump.user.User;
import com.github.f4b6a3.uuid.UuidCreator;

public class Item extends CatalogItem {
    private int price;
    private final User seller;
    private String imageName;
    private Category category;

    public Item(String title, String description, int price, User seller,String imageName, Category category) {
        super(UuidCreator.getTimeOrderedEpoch().toString(), title, description);
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
    public User getSeller() {
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
