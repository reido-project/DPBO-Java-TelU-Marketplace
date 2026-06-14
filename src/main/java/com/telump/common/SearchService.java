package com.telump.common;

import com.telump.database.*;
import com.telump.catalog.*;
import com.telump.user.User;

import java.util.ArrayList;
import java.util.List;

public class SearchService {
    public static List<CatalogItem> globalSearch(String query){
        List<CatalogItem> items = new ArrayList<>();

        if(query == null || query.trim().isBlank()){
            return getRandomItems();
        }
        
        if(query.startsWith("@")){
            items.addAll(searchUser(query.substring(1)));
        }
        else{
            items.addAll(searchByTag(query));

            if(items.isEmpty()){
                items.addAll(searchByTitle(query));
            }
        }

        if(items.isEmpty()){
            return getRandomItems();
        }

        return injectAds(items);
    }

    public static List<CatalogItem> getRandomItems(){
        return injectAds(catalogConvert(ItemTable.getRandomItems()));
    }

    private static List<CatalogItem> searchByTag(String query){
        return catalogConvert(ItemTable.getItemsByCategory(query));
    }

    private static List<CatalogItem> searchByTitle(String query){
        return catalogConvert(ItemTable.getItemsByTitle(query));
    }

    private static List<CatalogItem> searchUser(String query){
        List<User> users = new ArrayList<>();
        List<CatalogItem> catalogItems = new ArrayList<>();

        if(users == null || users.isEmpty()){
            return catalogItems;
        }
        
        for(User u : users){
            catalogItems.add(new CatalogItem(u.getUsername(), u.getProfilname(), u.getBio()));
        }

        return catalogItems;
    }

    private static List<CatalogItem> catalogConvert(List<Item> items){
        List<CatalogItem> catalogItems = new ArrayList<>();

        if(items == null || items.isEmpty()){
            return catalogItems;
        }

        for(Item i : items){
            catalogItems.add(new CatalogItem(i.getId(), i.getTitle(), i.getFormattedPrice()));
        }

        return catalogItems;
    }

    private static List<CatalogItem> injectAds(List<CatalogItem> items){
        List<CatalogItem> finalItems = new ArrayList<>();
        
        if (items == null || items.isEmpty()) {
            return finalItems;
        }

        for (int i = 0; i < items.size(); i++) {
            finalItems.add(items.get(i));
            
            if ((i + 1) % 9 == 0) {
                finalItems.add(AdTable.getRandomAd()); 
            }
        }
        
        return finalItems;
    }
}
