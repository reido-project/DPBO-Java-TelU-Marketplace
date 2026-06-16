package com.telump.page.profile;

import com.telump.catalog.Ad;
import com.telump.common.Util;
import com.telump.database.AdTable;

public class UpdateAd {
    public static void updateAd(String id){

        Ad selectAd = AdTable.getAdById(id);

        while(true){
            System.out.println("""
                =================================

                1. Edit Ad
                2. Delete Ad
                0. Kembali

                =================================
                """);
            int opsi = Util.integerInput("Pilih Opsi: ");
            switch(opsi){
                case 1:
                    editAd(selectAd);
                    System.out.println("-----------------------------\nberhasil diupdate\n-----------------------------");
                    continue;
                case 2:
                    deleteAd(selectAd);
                    System.out.println("-----------------------------\nberhasil dihapus\n-----------------------------");
                    return;
                case 0:
                    return;
                default:
                    continue;
            }
        }
    }

    private static void editAd(Ad selectedAd){
       int pilihan = 9;

            while(pilihan != 0){
                System.out.println("\n========== EDIT ITEM ==========");
                System.out.println("Pilih field yang ingin diupdate:");
                System.out.println("1. Title");
                System.out.println("2. Description");
                System.out.println("3. TargetUrl");
                System.out.println("0. Kembali");
                System.out.println("=================================");

                pilihan = Util.integerInput("Field yang ingin di update: ");

                System.out.println("---------------------------------");

                switch (pilihan){
                    case 1:
                        String title = Util.stringInput("New Title: ");
                        selectedAd.setTitle(title);
                        break;
                    case 2:
                        String description = Util.stringInput("New Description: ");
                        selectedAd.setDescription(description);
                        break;
                    case 3:
                        String Url = Util.stringInput("New TargetUrl: ");
                        selectedAd.setTargetUrl(Url);
                        break;
                    case 0:
                        return;
                    default:
                        return;
                }
        }
    }
    private static String deleteAd (Ad selectedAd){
        return AdTable.delete(selectedAd.getId());
    }
}
