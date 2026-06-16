package com.telump.page.dashboard;

import java.util.Set;

import com.telump.AppController;
import com.telump.catalog.*;
import com.telump.common.State;
import com.telump.common.Util;
import com.telump.database.AdTable;
import com.telump.database.ItemTable;
import com.telump.database.UserTable;
import com.telump.page.AppInfo;
import com.telump.page.profile.Profile;
import com.telump.page.profile.UpdateAd;
import com.telump.page.profile.UpdateItem;
import com.telump.page.profile.UpdateProfile;
import com.telump.user.User;
import com.telump.common.AuthenticationService;
import com.telump.page.catalog.*;
import com.telump.common.SearchService;

public class Dashboard {
    public AppInfo dashboard(){
        String errorMessage = "";
        State appState = State.Dashboard;

        while(true){
            CatalogItem selectedItem = null;

            System.out.printf("""
                1. See Catalog
                2. Search Item or User
                3. View Profile
                4. Edit Profile
                5. Sell Item
                6. Register Ad
                7. See Your Active Items
                8. See Your Active Ads
                9. Logout
                0. Exit
                %s
                """, errorMessage);
            switch(Util.integerInput("Pilih opsi: ")) {
                case 1:
                    selectedItem = openCatalog(SearchService.globalSearch(null));
                    printItemInfo(selectedItem, "Input y Untuk Keluar: ");
                    continue;
                case 2:
                    String query = Util.stringInput("Cari: ");
                    selectedItem = openCatalog(SearchService.globalSearch(query));
                    printItemInfo(selectedItem, "Input y Untuk Keluar: ");
                    continue;
                case 3:
                    Profile.showProfile();
                    continue;
                case 4:
                    UpdateProfile.showUpdateProfileMenu();
                    continue;
                case 5:
                    AppInfo registerItemInfo = new RegisterItem().registerItem();
                    errorMessage = registerItemInfo.getErrorMessage();
                    appState = registerItemInfo.getAppState();
                    continue;
                case 6:
                    AppInfo registerAdInfo = new RegisterAdPage().registerAd();
                    errorMessage = registerAdInfo.getErrorMessage();
                    appState = registerAdInfo.getAppState();
                    continue;
                case 7:
                    selectedItem = openCatalog(SearchService.searchItemsByUsername(AppController.getSession().getUsername()));
                    if(printItemInfo(selectedItem, "Edit Item? (y/n): ")){
                        UpdateItem.updateItem(selectedItem.getId());
                    }
                    continue;
                case 8:
                    selectedItem = openCatalog(SearchService.searchAdsByUsername(AppController.getSession().getUsername()));
                    if(printItemInfo(selectedItem, "Edit Ad? (y/n): ")){
                        UpdateAd.updateAd(selectedItem.getId());
                    }
                    continue;
                case 9:
                    // Logout user dan hapus sesi
                    AuthenticationService.logout();
                    errorMessage = "Logout berhasil!";
                    appState = State.Start;
                    break;
                case 0:
                    errorMessage = null;
                    appState = State.Exit;
                    break;
            }

            return new AppInfo(errorMessage, appState);
        }
    }

    private CatalogItem openCatalog(Set<CatalogItem> items){
        Catalog window = new Catalog(items);
        window.setVisible(true);

        while (window.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return window.getSelectedItem();
    }

    private boolean printItemInfo(CatalogItem catalogItem, String message){
        if(catalogItem == null){
            System.out.println("\nNo Item Was Selected.");
            return false;
        }

        switch(catalogItem.getType()){
            case Item:
                Item item = ItemTable.getItemById(catalogItem.getId());
                System.out.printf("""

                        ID: %s
                        Judul: %s
                        Deskripsi: %s
                        Harga: %s
                        Kategori: %s
                        Penjual: @%s

                        """, item.getId(), item.getTitle(), item.getDescription(), item.getFormattedPrice(), item.getCategory().getName(), item.getSeller().getUsername());
                return Util.confirmation(message);
            case Ad:
                Ad ad = AdTable.getAdById(catalogItem.getId());
                System.out.printf("""
                        
                        ID: %s
                        Judul: %s
                        Deskripsi: %s
                        Pelajari Lebih Lanjut: %s
                        Pengiklan: @%s

                        """, ad.getId(), ad.getTitle(), ad.getDescription(), ad.getTargetUrl(), ad.getRegistrant().getUsername());
                return Util.confirmation(message);
            case User:
                User user = UserTable.findByUsername(catalogItem.getId());
                System.out.printf("""
                        
                        Username: @%s
                        Name: %s
                        Bio: %s
                        Kontak: %s
                        Role: %s

                        """, user.getUsername(), user.getProfilname(), user.getBio(), user.getKontak(), String.valueOf(user.getRole()));
                return Util.confirmation(message);
            default:
                System.out.println("\nNo Item Was Selected.");
                return false;
        }
    }
}
