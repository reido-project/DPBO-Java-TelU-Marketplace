package com.telump;

import com.telump.catalog.Ad;
import com.telump.database.AdTable;

public class Main {
    public static void main(String[] args) {
        init();

        AppController appController = new AppController();

        appController.appController();
    }
 
    public static void init(){ // Ini method buat testing
        AdTable.create(0, new Ad("Example001", "Tel-U Marketplace!", "Tel-U Marketplace, Platform JB Terbaik Untuk Mahasiswa Telkom University!", "", "Reido"));

        // Demo alur autentikasi: register -> login -> logout
        // demoAuthFlow();
    }

    // /**
    //  * Contoh penggunaan alur register -> login -> logout dengan data dummy
    //  * Method ini berjalan saat aplikasi pertama kali dijalankan (di init)
    //  */
    // private static void demoAuthFlow() {
    //     System.out.println("\n========== DEMO AUTH FLOW ==========");

    //     // 1. Register - kasus gagal: password terlalu pendek
    //     System.out.println("\n--- Register (password pendek) ---");
    //     User gagal = AuthenticationService.registerUser("john", "123");
    //     System.out.println("Hasil: " + (gagal == null ? "GAGAL - " + AuthenticationService.getLastError() : "BERHASIL"));

    //     // 2. Register - kasus gagal: username mengandung spasi
    //     System.out.println("\n--- Register (username invalid) ---");
    //     gagal = AuthenticationService.registerUser("john doe", "password123");
    //     System.out.println("Hasil: " + (gagal == null ? "GAGAL - " + AuthenticationService.getLastError() : "BERHASIL"));

    //     // 3. Register - kasus berhasil
    //     System.out.println("\n--- Register (berhasil) ---");
    //     User demoUser = AuthenticationService.registerUser("demo_user", "demo123", "Demo User", "081234567890", "Akun demo untuk testing");
    //     System.out.println("Hasil: " + (demoUser != null ? "BERHASIL - " + demoUser : "GAGAL"));

    //     // 4. Register - kasus gagal: username duplikat
    //     System.out.println("\n--- Register (username duplikat) ---");
    //     gagal = AuthenticationService.registerUser("demo_user", "lainnya123");
    //     System.out.println("Hasil: " + (gagal == null ? "GAGAL - " + AuthenticationService.getLastError() : "BERHASIL"));

    //     // 5. Login - kasus gagal: password salah
    //     System.out.println("\n--- Login (password salah) ---");
    //     boolean loginGagal = AuthenticationService.loginUser("demo_user", "salah123");
    //     System.out.println("Hasil: " + (loginGagal ? "BERHASIL" : "GAGAL - " + AuthenticationService.getLastError()));

    //     // 6. Login - kasus gagal: username tidak ada
    //     System.out.println("\n--- Login (username tidak ada) ---");
    //     loginGagal = AuthenticationService.loginUser("tidak_ada", "password123");
    //     System.out.println("Hasil: " + (loginGagal ? "BERHASIL" : "GAGAL - " + AuthenticationService.getLastError()));

    //     // 7. Login - kasus berhasil (menggunakan loginUser boolean)
    //     System.out.println("\n--- Login (berhasil) ---");
    //     boolean loginBerhasil = AuthenticationService.loginUser("demo_user", "demo123");
    //     System.out.println("Hasil: " + (loginBerhasil ? "BERHASIL" : "GAGAL"));
    //     System.out.println("Current user: " + AuthenticationService.getCurrentUser());

    //     // 8. Logout
    //     System.out.println("\n--- Logout ---");
    //     AuthenticationService.logout();
    //     System.out.println("Current user setelah logout: " + AuthenticationService.getCurrentUser());

    //     System.out.println("\n========== DEMO SELESAI ==========\n");
    // }
}
