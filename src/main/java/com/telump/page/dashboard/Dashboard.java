package com.telump.page.dashboard;

import java.util.Set;

import com.telump.AppController;
import com.telump.catalog.Ad;
import com.telump.catalog.CatalogItem;
import com.telump.catalog.Item;
import com.telump.common.AuthenticationService;
import com.telump.common.SearchService;
import com.telump.common.State;
import com.telump.common.Util;
import com.telump.database.AdTable;
import com.telump.database.ItemTable;
import com.telump.database.UserTable;
import com.telump.page.AppInfo;
import com.telump.page.catalog.Catalog;
import com.telump.page.detail.AdDetail;
import com.telump.page.detail.ItemDetail;
import com.telump.page.detail.UserDetail;
import com.telump.page.profile.Profile;
import com.telump.page.profile.UpdateAd;
import com.telump.page.profile.UpdateItem;
import com.telump.page.profile.UpdateProfile;
import com.telump.user.User;

public class Dashboard {
    public AppInfo dashboard(){
        String errorMessage = "";
        State appState = State.Dashboard;

        while(true){
            errorMessage = "Dashboard";
            CatalogItem selectedItem = null;

            System.out.print("""
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
                """);
            switch(Util.integerInput("Pilih opsi: ")) {
                case 1:
                    selectedItem = openCatalog(SearchService.globalSearch(null));
                    printItemInfo(selectedItem);
                    break;
                case 2:
                    String query = Util.stringInput("Cari: ");
                    selectedItem = openCatalog(SearchService.globalSearch(query));
                    printItemInfo(selectedItem);
                    break;
                case 3:
                    Profile.showProfile();
                    break;
                case 4:
                    UpdateProfile.showUpdateProfileMenu();
                    break;
                case 5:
                    AppInfo registerItemInfo = new RegisterItem().registerItem();
                    errorMessage = registerItemInfo.getErrorMessage();
                    appState = registerItemInfo.getAppState();
                    break;
                case 6:
                    AppInfo registerAdInfo = new RegisterAdPage().registerAd();
                    errorMessage = registerAdInfo.getErrorMessage();
                    appState = registerAdInfo.getAppState();
                    break;
                case 7:
                    selectedItem = openCatalog(SearchService.searchItemsByUsername(AppController.getSession().getUsername()));
                    printItemInfo(selectedItem);
                    if (selectedItem != null && Util.confirmation("Edit Item ini? (y/n): ")) {
                        UpdateItem.updateItem(selectedItem.getId());
                    }
                    break;
                case 8:
                    selectedItem = openCatalog(SearchService.searchAdsByUsername(AppController.getSession().getUsername()));
                    printItemInfo(selectedItem);
                    if (selectedItem != null && Util.confirmation("Edit Ad ini? (y/n): ")) {
                        UpdateAd.updateAd(selectedItem.getId());
                    }
                    break;
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

    private void printItemInfo(CatalogItem catalogItem){
        if(catalogItem == null){
            System.out.println("\nNo Item Was Selected.");
            return;
        }

        switch(catalogItem.getType()){
            case Item:
                Item item = ItemTable.getItemById(catalogItem.getId());
                ItemDetail itemDetail = new ItemDetail(item);
                itemDetail.show();
                break;
            case Ad:
                Ad ad = AdTable.getAdById(catalogItem.getId());
                AdDetail adDetail = new AdDetail(ad);
                adDetail.show();
                break;
            case User:
                User user = UserTable.findByUsername(catalogItem.getId());
                UserDetail userDetail = new UserDetail(user);
                userDetail.show();
                break;
            default:
                System.out.println("\nNo Item Was Selected.");
        }
    }
}
