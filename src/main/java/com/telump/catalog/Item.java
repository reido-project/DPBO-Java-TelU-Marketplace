package com.telump.catalog;

public class Item extends CatalogItem {
    private int price;
    private final String seller;

    public Item(String id, String title, String description, int price, String seller) {
        super(id, title, description);
        this.price = price;
        this.seller = seller;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public String getSeller() {
            return seller;
    }
}
