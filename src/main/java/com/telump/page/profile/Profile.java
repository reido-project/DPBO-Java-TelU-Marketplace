package com.telump.page.profile;

import com.telump.common.AuthenticationService;
import com.telump.common.Util;
import com.telump.user.User;

public class Profile {
    
    public static void showProfile() {
        User user = AuthenticationService.getCurrentUser();
        if (user != null) {
            System.out.println("\n========== PROFIL USER ==========");
            System.out.println("Username     : " + user.getUsername());
            
            String profilName = user.getProfilname();
            if (profilName != null && !profilName.isEmpty()) {
                System.out.println("Nama Lengkap : " + profilName);
            } else {
                System.out.println("Nama Lengkap : -");
            }
            
            String kontak = user.getKontak();
            if (kontak != null && !kontak.isEmpty()) {
                System.out.println("Kontak        : " + kontak);
            } else {
                System.out.println("Kontak        : -");
            }
            
            System.out.println("Role/Level   : " + user.displayRole());
            
            String bio = user.getBio();
            if (bio != null && !bio.isEmpty()) {
                System.out.println("Bio          : " + bio);
            } else {
                System.out.println("Bio          : -");
            }
            
            System.out.println("Status       : " + user.getStatus());
            
            System.out.println("=================================");
            System.out.println("1. Kembali");
            System.out.println("2. Edit Profil");
            
            int pilihan = Util.integerInput("Pilih opsi: ");
            
            switch(pilihan) {
                case 1:
                    break;
                case 2:
                    UpdateProfile.showUpdateProfileMenu();
                    break;
                default:
                    System.out.println("[Error] Opsi tidak valid!");
            }
        } else {
            System.out.println("\n[Error] Tidak ada user yang sedang login.");
            Util.stringInput("\nTekan Enter untuk kembali...");
        }
    }
}
