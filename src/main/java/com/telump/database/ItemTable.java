package com.telump.database;

import com.telump.catalog.Item;
import com.telump.category.Category;

import java.util.ArrayList;
import java.util.List;

public class ItemTable implements IDatabase{
    private static final List<Item> itemTable = new ArrayList<>();

    public static int getItemIndexById(String id) {
        for (int i = 0; i < itemTable.size(); i++) {
            if (itemTable.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public static String create(Item item) {
        for (Item i : itemTable) {
            if (i.getId().equals(item.getId())) {
                return "Error: Item with duplicated id found.";
            }
        }
        
        itemTable.add(item);
        return "Item registered successfully!";
    }

    public static String create(int index, Item item) {
        for (Item i : itemTable) {
            if (i.getId().equals(item.getId())) {
                return "Error: Item with duplicated id found.";
            }
        }

        if (index >= 0 && index <= itemTable.size()) {
            itemTable.add(index, item);
            return "Item registered successfully!";
        } else {
            return "Error: Index out of bounds.";
        }
    }

    public static List<Item> getItemListById(String id) {
        List<Item> items = new ArrayList<>();
        for (Item item : itemTable) {
            if (item.getId().equals(id)) {
                items.add(item);
            }
        }
        return items;
    }

    public static String update(Item item) {
        int targetItemIndex = getItemIndexById(item.getId());

        if (targetItemIndex == -1) {
            return "Error: Item not found.";
        }

        itemTable.set(targetItemIndex, item);
        return "Item updated successfully!";
    }
    
    public static String delete(String id) {
        int targetItemIndex = getItemIndexById(id);

        if (targetItemIndex == -1) {
            return "Error: Item not found.";
        }

        itemTable.remove(targetItemIndex);
        return "Item deleted successfully!";
    }
    public static List<Item> searchByCategory(String categoryName) {
        List<Item> items = new ArrayList<>();

        for (Item item : itemTable) {
            if (item.getCategory() != null &&
                item.getCategory().getName().equalsIgnoreCase(categoryName)) {

                items.add(item);
            }
        }
        return items;
    }
}
