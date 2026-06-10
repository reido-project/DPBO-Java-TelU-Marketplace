package com.telump.common;

import com.telump.database.UserTable;
import com.telump.user.RegularUser;
import com.telump.user.User;

/**
 * Service untuk mengelola autentikasi user (register, login, logout)
 * Menyimpan sesi user yang sedang login via currentUser
 */
public class AuthenticationService {

    // Sesi user yang sedang login saat ini
    private static User currentUser = null;

    // Pesan error terakhir dari operasi yang gagal
    private static String lastError = null;

    /**
     * Getter untuk user yang sedang login
     * @return User yang sedang login, null jika belum ada sesi
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Getter untuk pesan error terakhir
     * @return pesan error terakhir, null jika operasi terakhir berhasil
     */
    public static String getLastError() {
        return lastError;
    }

    /**
     * Autentikasi user berdasarkan username dan password
     * @param username username user
     * @param password password user
     * @return User jika autentikasi berhasil, null jika gagal
     */
    public static User authenticateUser(String username, String password) {
        User user = UserTable.findByUsername(username);
        
        if (user != null && user.login(username, password)) {
            return user;
        }
        
        return null;
    }

    /**
     * Login user dan simpan sesi ke currentUser
     * @param username username user
     * @param password password user
     * @return User jika login berhasil, null jika gagal
     */
    public static User login(String username, String password) {
        lastError = null;

        // Validasi input tidak kosong
        if (username == null || username.trim().isEmpty()) {
            lastError = "Username tidak boleh kosong!";
            System.out.println(lastError);
            return null;
        }
        if (password == null || password.trim().isEmpty()) {
            lastError = "Password tidak boleh kosong!";
            System.out.println(lastError);
            return null;
        }

        // Cek apakah username terdaftar
        User user = UserTable.findByUsername(username);
        if (user == null) {
            lastError = "Username '" + username + "' tidak ditemukan!";
            System.out.println(lastError);
            return null;
        }

        // Cocokkan password
        if (!user.login(username, password)) {
            lastError = "Password salah untuk user '" + username + "'!";
            System.out.println(lastError);
            return null;
        }

        // Login berhasil, simpan sesi
        currentUser = user;
        lastError = null;
        System.out.println("Login berhasil! Selamat datang, " + user.getUsername() + ".");
        return currentUser;
    }

    /**
     * Login user dan return boolean untuk kemudahan pengecekan di menu
     * @param username username user
     * @param password password user
     * @return true jika login berhasil, false jika gagal (cek getLastError() untuk detail)
     */
    public static boolean loginUser(String username, String password) {
        return login(username, password) != null;
    }

    /**
     * Logout user dan hapus sesi currentUser
     */
    public static void logout() {
        if (currentUser != null) {
            System.out.println(currentUser.getUsername() + " telah logout.");
            currentUser.logout();
            currentUser = null;
            lastError = null;
        } else {
            lastError = "Tidak ada user yang sedang login.";
            System.out.println(lastError);
        }
    }

    /**
     * Register user baru dengan username dan password saja (field lain dikosongkan)
     * Validasi: username tidak boleh kosong, username unik, password minimal 6 karakter
     * @param username username user baru
     * @param password password user baru (minimal 6 karakter)
     * @return User baru jika registrasi berhasil, null jika gagal
     */
    public static User registerUser(String username, String password) {
        return registerUser(username, password, username, "", "");
    }

    /**
     * Validasi data registrasi sebelum menyimpan user baru
     * Bisa dipanggil terpisah untuk mendapatkan pesan error spesifik
     * @param username username yang akan divalidasi
     * @param password password yang akan divalidasi
     * @return null jika valid, String pesan error jika tidak valid
     */
    public static String validateRegistration(String username, String password) {
        // Validasi username tidak boleh kosong
        if (username == null || username.trim().isEmpty()) {
            return "Username tidak boleh kosong!";
        }

        // Validasi username minimal 3 karakter
        if (username.trim().length() < 3) {
            return "Username minimal 3 karakter!";
        }

        // Validasi username hanya boleh huruf, angka, dan underscore
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            return "Username hanya boleh mengandung huruf, angka, dan underscore!";
        }

        // Validasi password tidak boleh kosong
        if (password == null || password.trim().isEmpty()) {
            return "Password tidak boleh kosong!";
        }

        // Validasi password minimal 6 karakter
        if (password.length() < 6) {
            return "Password minimal 6 karakter! (saat ini: " + password.length() + " karakter)";
        }

        // Cek duplikasi username di ArrayList
        if (!isUsernameUnique(username)) {
            return "Username '" + username + "' sudah digunakan!";
        }

        return null; // Valid, tidak ada error
    }

    /**
     * Register user baru dengan data lengkap
     * Menggunakan validateRegistration() untuk validasi, set lastError jika gagal
     * @param username username user baru
     * @param password password user baru (minimal 6 karakter)
     * @param profilname nama profil user
     * @param kontak informasi kontak user
     * @param bio bio singkat user
     * @return User baru jika registrasi berhasil, null jika gagal (cek getLastError())
     */
    public static User registerUser(String username, String password, String profilname, String kontak, String bio) {
        lastError = null;

        // Validasi username dan password
        String validationError = validateRegistration(username, password);
        if (validationError != null) {
            lastError = validationError;
            System.out.println(lastError);
            return null;
        }

        // Validasi profilname tidak boleh kosong
        if (profilname == null || profilname.trim().isEmpty()) {
            lastError = "Nama profil tidak boleh kosong!";
            System.out.println(lastError);
            return null;
        }

        // Buat user baru sebagai RegularUser
        User newUser = new RegularUser(username, password, profilname, kontak, bio);

        // Simpan ke database (ArrayList)
        if (UserTable.save(newUser)) {
            lastError = null;
            System.out.println("Registrasi user '" + username + "' berhasil!");
            return newUser;
        }

        lastError = "Gagal menyimpan user ke database!";
        System.out.println(lastError);
        return null;
    }

    public static boolean isUsernameUnique(String username) {
        return UserTable.findByUsername(username) == null;
    }
}
