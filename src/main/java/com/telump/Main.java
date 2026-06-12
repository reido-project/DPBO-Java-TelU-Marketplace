package com.telump;

import com.telump.catalog.Ad;
import com.telump.database.AdTable;
import com.telump.database.UserTable;
import com.telump.user.Admin;
import com.telump.user.RegularUser;

public class Main {
    public static void main(String[] args) {
        init();

        AppController appController = new AppController();

        appController.appController();
    }
 
    public static void init(){ 
        // Initialize default ads
        AdTable.create(0, new Ad("Example001", "Tel-U Marketplace!", "Tel-U Marketplace, Platform JB Terbaik Untuk Mahasiswa Telkom University!", "", "Reido"));

        // Initialize default users for testing (HARDCODED)
        initializeDefaultUsers();
    }

    /**
     * Inisialisasi akun default untuk testing:
     * - 1 akun ADMIN: admin / admin123
     * - 1 akun USER biasa: user / user123
     */
    public static void initializeDefaultUsers() {
        // Create Admin account
        Admin adminUser = new Admin("admin", "admin123", "Administrator", "admin@telu.ac.id", "Admin account for Tel-U Marketplace");
        UserTable.save(adminUser);
        System.out.println("[Init] Default ADMIN account created: admin / admin123");

        // Create Regular User account
        RegularUser regularUser = new RegularUser("user", "user123", "Regular User", "user@student.telu.ac.id", "Regular user account for testing");
        UserTable.save(regularUser);
        System.out.println("[Init] Default USER account created: user / user123");
    }
}
