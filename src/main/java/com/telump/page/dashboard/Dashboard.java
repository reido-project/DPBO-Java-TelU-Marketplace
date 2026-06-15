package com.telump.page.dashboard;

import java.util.List;

import com.telump.catalog.*;
import com.telump.common.State;
import com.telump.common.Util;
import com.telump.database.AdTable;
import com.telump.database.ItemTable;
import com.telump.database.UserTable;
import com.telump.page.AppInfo;
import com.telump.page.profile.Profile;
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
            System.out.printf("""
                1. See Catalog
                2. Search Item or User
                3. View Profile
                4. Edit Profile
                5. Sell Item
                6. Register Ad
                9. Logout
                0. Exit
                %s\n
                """, errorMessage);
            switch(Util.integerInput("Pilih opsi: ")) {
                case 1:
                    CatalogItem selectedRandom = openCatalog(SearchService.getRandomItems());
                    printItemInfo(selectedRandom);
                    continue;
                case 2:
                    String query = Util.stringInput("Cari: ");
                    CatalogItem selectedSearch = openCatalog(SearchService.globalSearch(query));
                    printItemInfo(selectedSearch);
                    continue;
                case 3:
                    Profile.showProfile();
                    continue;
                case 4:
                    UpdateProfile.showUpdateProfileMenu();
                    continue;
                case 5:
                    continue;
                case 6:
                    AppInfo registerAdInfo = new RegisterAdPage().registerAd();
                    errorMessage = registerAdInfo.getErrorMessage();
                    appState = registerAdInfo.getAppState();
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

    private CatalogItem openCatalog(List<CatalogItem> items){
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

    private void printItemInfo(CatalogItem catalogItem){
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
                return;
            case Ad:
                Ad ad = AdTable.getAdById(catalogItem.getId());
                System.out.printf("""
                        
                        ID: %s
                        Judul: %s
                        Deskripsi: %s
                        Pelajari Lebih Lanjut: %s
                        Pengiklan: @%s

                        """, ad.getId(), ad.getTitle(), ad.getDescription(), ad.getTargetUrl(), ad.getRegistrant().getUsername());
                return;
            case User:
                User user = UserTable.findByUsername(catalogItem.getId());
                System.out.printf("""
                        
                        Username: @%s
                        Name: %s
                        Bio: %s
                        Kontak: %s
                        Role: %s

                        """, user.getUsername(), user.getProfilname(), user.getBio(), user.getKontak(), String.valueOf(user.getRole()));
                return;
            default:
                System.out.println("\nNo Item Selected.");
                return;
        }
    }
}
