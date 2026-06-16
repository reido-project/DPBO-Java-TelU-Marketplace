package com.telump.page.profile;

import com.telump.catalog.Ad;
import com.telump.common.Util;
import com.telump.database.AdTable;
import com.telump.user.User;

public class UpdateAd {
    public static void updateAd(){
        while(true){
            System.out.println("""

                1. Edit Ad
                2. Delete Ad
                0. Kembali

                """);
            int opsi = Util.integerInput("Pilih Opsi: ");
            switch(opsi){
                case 1:
                    System.out.println(editAd());
                    continue;
                case 2:
                    System.out.println(deleteAd());
                    continue;
                case 0:
                    return;
                default:
                    continue;
            }
        }
    }

    private static String editAd(){
        String id = Util.stringInput("Masukkan ID Iklan: ");
        Ad ad = AdTable.getAdById(id);

        if(ad == null){
            return "Iklan tidak ditemukan.";
        }

        String newTitle = Util.stringInput("Masukkan Judul Yang Baru: ");
        String newDescription = Util.stringInput("Masukkan Deskripsi Yang Baru: ");
        String newTargetUrl = Util.stringInput("Masukkan URL Target Yang baru: ");
        User registrant = ad.getRegistrant();
        String imagePath = ad.getImagePath();

        Ad newAd = new Ad(id, newTitle, newDescription, newTargetUrl, registrant, imagePath);
        return AdTable.update(newAd);
    }

    private static String deleteAd(){
        String id = Util.stringInput("Masukkan ID Iklan: ");
        return AdTable.delete(id);
    } 
}
