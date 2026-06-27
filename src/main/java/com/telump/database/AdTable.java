package com.telump.database;

import com.telump.catalog.Ad;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdTable implements IDatabase{
    private static final List<Ad> adTable = new ArrayList<>();

    public static String create(Ad ad){
        for(Ad a : adTable){
            if(a.getId().equals(ad.getId())){
                return "Error: Ad with duplicated id found.";
            }
        }

        adTable.add(ad);
        return "Ad registered successfully!";
    }

    // Method buat testing
    public static String create(int index, Ad ad){
        for(Ad a : adTable){
            if(a.getId().equals(ad.getId())){
                return "Error: Ad with duplicated id found.";
            }
        }

        adTable.add(index, ad);
        return "Ad registered successfully!";
    }

    public static Ad getAdById(String id){
        for(Ad ad : adTable){
            if(ad.getId().equals(id)){
                return ad;
            }
        }

        return null;
    }

    public static List<Ad> getAdListById(String id){
        List<Ad> ads = new ArrayList<>();

        for(Ad ad : adTable){
            if(ad.getId().equals(id)){
                ads.add(ad);
            }
        }

        return ads;
    }

    public static int getAdIndexById(String id){
        for(int i = 1; i < adTable.size(); i++){
            if(adTable.get(i).getId().equals(id)){
                return i;
            }
        }
        return 0;
    }

    public static List<Ad> getAdsByUsername(String username){
        List<Ad> ads = new ArrayList<>();

        for(Ad ad : adTable){
            if(ad.getRegistrant().getUsername().equals(username)){
                ads.add(ad);
            }
        }

        return ads;
    }

    public static Ad getDefaultAd(){
        return adTable.getFirst();
    }

    public static Ad getRandomAd(){
        if(adTable.size() == 1){
            return adTable.getFirst();
        }
        else if(adTable.size() == 2){
            return adTable.get(1);
        }

        Random rand = new Random();
        int randNum = rand.nextInt(1, adTable.size());
        return adTable.get(randNum);
    }

    public static int getAdsCountByUsername(String username){
        return getAdsByUsername(username).size();
    }

    public static String update(Ad ad){
        int targetAdIndex = getAdIndexById(ad.getId());

        if(targetAdIndex == 0){
            return "Error: Ad not found.";
        }

        adTable.set(targetAdIndex, ad);
        return "Ad updated successfully!";
    }

    public static String delete(String id){
        int targetAdIndex = getAdIndexById(id);

        if(targetAdIndex == 0){
            return "Error: Ad not found.";
        }

        adTable.remove(targetAdIndex);
        return "Ad deleted successfully!";
    }
}