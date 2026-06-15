package com.telump.common;

import com.telump.database.*;
import com.telump.catalog.*;
import com.telump.user.User;

import java.util.ArrayList;
import java.util.List;

public class SearchService {
    public static List<CatalogItem> globalSearch(String query){
        List<CatalogItem> items = new ArrayList<>();
        String trimmedQuery = query.trim();

        if(trimmedQuery == null || trimmedQuery.isBlank()){
            return getRandomItems();
        }
        
        if(trimmedQuery.startsWith("@")){
            items.addAll(searchUser(trimmedQuery.substring(1)));
        }
        else{
            items.addAll(searchByTag(trimmedQuery));

            if(items.isEmpty()){
                items.addAll(searchByTitle(trimmedQuery));
            }
        }

        if(items.isEmpty()){
            return getRandomItems();
        }

        return injectAds(items);
    }

    public static List<CatalogItem> getRandomItems(){
        return injectAds(ItemCatalogConvert(ItemTable.getRandomItems()));
    }

    private static List<CatalogItem> searchByTag(String query){
        return ItemCatalogConvert(ItemTable.getItemsByCategory(query));
    }

    private static List<CatalogItem> searchByTitle(String query){
        return ItemCatalogConvert(ItemTable.getItemsByTitle(query));
    }

    private static List<CatalogItem> searchUser(String query){
        return UserCatalogConvert(UserTable.getUsersByTitle(query));
    }

    private static List<CatalogItem> ItemCatalogConvert(List<Item> items){
        List<CatalogItem> catalogItems = new ArrayList<>();

        if(items == null || items.isEmpty()){
            return catalogItems;
        }

        for(Item i : items){
            catalogItems.add(new CatalogItem(i.getId(), i.getTitle(), i.getFormattedPrice(), ItemType.Item, i.getImagePath()));
        }

        return catalogItems;
    }

    private static CatalogItem AdCatalogConvert(Ad ad){
        if(ad == null){
            Ad nullAd = AdTable.getDefaultAd();
            return new CatalogItem(nullAd.getId(), nullAd.getTitle(), nullAd.getDescription(), ItemType.Ad, nullAd.getImagePath());
        }

        return new CatalogItem(ad.getId(), ad.getTitle(), ad.getDescription(), ItemType.Ad, ad.getImagePath());
    }

    private static List<CatalogItem> UserCatalogConvert(List<User> users){
        List<CatalogItem> catalogItems = new ArrayList<>();

        if(users == null || users.isEmpty()){
            return catalogItems;
        }

        for(User u : users){
            catalogItems.add(new CatalogItem(u.getUsername(), u.getProfilname(), u.getBio(), ItemType.User, u.getImagePath()));
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
                finalItems.add(AdCatalogConvert(AdTable.getRandomAd())); 
            }
        }
        
        return finalItems;
    }
}
