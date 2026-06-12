package com.telump.page.dashboard;

import java.util.List;

import com.telump.catalog.CatalogItem;
import com.telump.common.State;
import com.telump.common.Util;
import com.telump.page.AppInfo;
import com.telump.page.profile.Profile;
import com.telump.page.profile.UpdateProfile;
import com.telump.common.AuthenticationService;
import com.telump.page.catalog.*;
import com.telump.catalog.*;
import com.telump.common.SearchService;

public class Dashboard {
    public AppInfo dashboard(){
        String errorMessage = null;
        State appState = State.Dashboard;

        while(true){
            System.out.println("""
                1. See Catalog
                2. Search Item or User
                3. View Profile
                4. Edit Profile
                9. Logout
                0. Exit
                """);
            switch(Util.integerInput("Pilih opsi: ")) {
                case 1:
                    openCatalog(SearchService.getRandomItems());
                    break;
                case 2:
                    String query = Util.stringInput("Cari: ");
                    openCatalog(SearchService.globalSearch(query));
                    break;
                case 3:
                    Profile.showProfile();
                    break;
                case 4:
                    UpdateProfile.showUpdateProfileMenu();
                    break;
                case 9:
                    // Logout user dan hapus sesi
                    AuthenticationService.logout();
                    return new AppInfo("Logout berhasil!", State.Start);
                case 0:
                    return new AppInfo(null, State.Exit);
            }
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
}
