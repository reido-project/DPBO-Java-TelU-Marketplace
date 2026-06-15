package com.telump.page.dashboard;

import com.telump.page.AppInfo;
import com.telump.user.User;
import com.telump.AppController;
import com.telump.catalog.Ad;
import com.telump.common.ItemType;
import com.telump.common.State;
import com.telump.common.Util;
import com.telump.database.AdTable;

public class RegisterAdPage {
    public AppInfo registerAd(){
        String errorMessage = "";
        State appState = State.RegisterAd;

        boolean confirm = Util.confirmation("Lanjut registrasi iklan? (y/n): ");

        if(confirm){
            Ad registeredAd = register();

            boolean confirmRegister = Util.confirmation("Apa Anda Yakin? (y/n): ");

            if(confirmRegister){
                errorMessage = AdTable.create(registeredAd);
                appState = State.Dashboard;
                return new AppInfo(errorMessage, appState);
            }
            else{
                errorMessage = "Registrasi iklan dibatalkan.";
                appState = State.Dashboard;
                return new AppInfo(errorMessage, appState);
            }
        }
        else{
            appState = State.Dashboard;
            return new AppInfo(errorMessage, appState);
        }
    }

    private Ad register(){
        String id = Util.generateUUID();
        String title = Util.stringInput("Masukkan Judul Iklan: ");
        String description = Util.stringInput("Masukkan Deskripsi Iklan: ");
        String targetUrl = Util.stringInput("Masukkan URL Target Iklan: ");
        
        String imagePath = Util.handleImageUpload(id, ItemType.Ad);
        User registrant = AppController.getSession();

        return new Ad(id, title, description, targetUrl, registrant, imagePath);
    }
}
