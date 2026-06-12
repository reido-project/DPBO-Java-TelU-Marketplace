package com.telump.page.dashboard;

import com.telump.catalog.Item;
import com.telump.database.ItemTable;
import com.telump.category.Category;

public class RegisterItem {
     public static String registerItem(String id, String title, String description, int price, String seller, Category category) {

        if (id == null || id.isEmpty()) {
            return "ID tidak boleh kosong";
        }

        if (title == null || title.isEmpty()) {
            return "Judul tidak boleh kosong";
        }

        if (price < 0) {
            return "Harga tidak valid";
        }

        String imageName = id + ".png";

        Item item = new Item(id, title, description, price, seller, imageName, category);
        return ItemTable.create(item);
        
    }

}
