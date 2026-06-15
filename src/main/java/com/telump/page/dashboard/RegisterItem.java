package com.telump.page.dashboard;

import com.telump.AppController;
import com.telump.catalog.Item;
import com.telump.database.CategoryTable;
import com.telump.database.ItemTable;
import com.telump.page.AppInfo;
import com.telump.user.User;
import com.telump.category.Category;
import com.telump.common.ItemType;
import com.telump.common.State;
import com.telump.common.Util;

public class RegisterItem {
    public AppInfo registerItem(){
        String errorMessage = "";
        State appState = State.RegisterItem;

        String id = Util.generateUUID();
        String title = Util.stringInput("Title: ");
        int price = Util.integerInput("Price: ");
        String description = Util.stringInput("Description: ");
        String categoryName = Util.stringInput("Category: ");
        String imagePath = Util.handleImageUpload(id, ItemType.Item);

        //cari category
        Category category = CategoryTable.getCategoryByName(categoryName);
        
        if (category == null) {
        errorMessage = "Kategori tidak ditemukan.";
            new AppInfo(errorMessage, State.RegisterItem);
        }
        //Mengambil user
        User currentUser = AppController.getSession();
        //instansiasi Item type shi
        Item item = new Item(id, title, description, price, currentUser, category, imagePath);

        String result = ItemTable.create(item);
        
        if (result.equals("Item registered successfully!")) {
            errorMessage = "Item berhasil didaftarkan.";
            appState = State.Dashboard;
        } else {
            errorMessage = result;
        }

        return new AppInfo(errorMessage, appState);
    } 
}
