package com.telump.common;

import com.telump.database.*;
import com.telump.catalog.*;
import com.telump.user.User;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.List;

public class SearchService {
    public static Set<CatalogItem> globalSearch(String query){
        Set<CatalogItem> items = new LinkedHashSet<>();
    
        if (query == null || query.isBlank()) {
            items.addAll(getRandomItems());
            return injectAds(items);
        }
        
        String trimmedQuery = query.trim();
        
        if(trimmedQuery.startsWith("@")){
            items.addAll(searchUsers(trimmedQuery.substring(1)));
        }
        else{
            items.addAll(searchById(trimmedQuery));

            if(!items.isEmpty()){
                return injectAds(items);
            }

            items.addAll(searchByCategory(trimmedQuery));
            items.addAll(searchByTitle(trimmedQuery));
        }

        items.addAll(getRandomItems());

        return injectAds(items);
    }

    public static Set<CatalogItem> getRandomItems(){
        return itemCatalogConvert(ItemTable.getRandomItems());
    }

    private static Set<CatalogItem> searchById(String query){
        return itemCatalogConvert(ItemTable.getItemListById(query));
    }

    private static Set<CatalogItem> searchByCategory(String query){
        return itemCatalogConvert(ItemTable.getItemsByCategory(query));
    }

    private static Set<CatalogItem> searchByTitle(String query){
        return itemCatalogConvert(ItemTable.getItemsByTitle(query));
    }

    private static Set<CatalogItem> searchUsers(String query){
        return userCatalogConvert(UserTable.getUsersByTitle(query));
    }

    public static Set<CatalogItem> searchItemsByUsername(String username){
        return itemCatalogConvert(ItemTable.getItemsByUsername(username));
    }

    public static Set<CatalogItem> searchAdsByUsername(String username){
        return adCatalogConvert(AdTable.getAdsByUsername(username));
    }

    private static Set<CatalogItem> itemCatalogConvert(List<Item> items){
        Set<CatalogItem> catalogItems = new LinkedHashSet<>();

        if(items == null || items.isEmpty()){
            return catalogItems;
        }

        for(Item i : items){
            catalogItems.add(new CatalogItem(i.getId(), i.getTitle(), i.getFormattedPrice(), ItemType.Item, i.getImagePath(), null));
        }

        return catalogItems;
    }

    private static CatalogItem adCatalogConvert(Ad ad){
        if(ad == null){
            Ad nullAd = AdTable.getDefaultAd();
            return new CatalogItem(nullAd.getId(), nullAd.getTitle(), nullAd.getDescription(), ItemType.Ad, nullAd.getImagePath(), UUID.randomUUID().toString());
        }

        return new CatalogItem(ad.getId(), ad.getTitle(), ad.getDescription(), ItemType.Ad, ad.getImagePath(), UUID.randomUUID().toString());
    }

    private static Set<CatalogItem> adCatalogConvert(List<Ad> ads){
        Set<CatalogItem> catalogItems = new LinkedHashSet<>();

        if(ads == null || ads.isEmpty()){
            return catalogItems;
        }

        for(Ad ad : ads){
            catalogItems.add(new CatalogItem(ad.getId(), ad.getTitle(), ad.getDescription(), ItemType.Ad, ad.getImagePath(), UUID.randomUUID().toString()));
        }

        return catalogItems;
    }

    private static Set<CatalogItem> userCatalogConvert(List<User> users){
        Set<CatalogItem> catalogItems = new LinkedHashSet<>();

        if(users == null || users.isEmpty()){
            return catalogItems;
        }

        for(User u : users){
            catalogItems.add(new CatalogItem(u.getUsername(), u.getProfilname(), u.getBio(), ItemType.User, u.getImagePath(), null));
        }

        return catalogItems;
    }

    private static Set<CatalogItem> injectAds(Set<CatalogItem> items){
        Set<CatalogItem> finalItems = new LinkedHashSet<>();
        
        if (items == null || items.isEmpty()) {
            return finalItems;
        }

        int index = 1;
        for (CatalogItem item : items) {
            finalItems.add(item);
            
            if (index % 9 == 0) {
                finalItems.add(adCatalogConvert(AdTable.getRandomAd())); 
            }
            index++;
        }
        
        return finalItems;
    }
}