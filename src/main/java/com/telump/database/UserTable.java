package com.telump.database;

import com.telump.user.User;
import com.telump.user.UserRole;

import java.util.ArrayList;
import java.util.List;

/**
 * Database table untuk User
 * Mengimplementasikan CRUD operations (Create, Read, Update, Delete)
 * Saat ini menggunakan in-memory List (bisa di-replace dengan database sebenarnya)
 */
public class UserTable implements IDatabase {
    private static final List<User> userTable = new ArrayList<>();

    /**
     * Cari user berdasarkan username
     * @param username username yang dicari
     * @return User object jika ditemukan, null jika tidak ada
     */
    public static User findByUsername(String username) {
        for (User user : userTable) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static List<User> getUsersByTitle(String query) {
        List<User> results = new ArrayList<>();

        if (query == null || query.trim().isEmpty()) {
            return null;
        }

        String lowerCaseQuery = query.toLowerCase();

        for (User user : userTable) {
            if (user.getUsername() != null && user.getUsername().toLowerCase().contains(lowerCaseQuery)) {
                results.add(user);
            }
        }

        return results;
    }

    public static User getRandomUser() {
        // Validasi: Jika ukuran list kurang dari 3, berarti tidak ada index >= 2
        if (userTable.size() < 3) {
            System.out.println("Gagal mengambil random user: Jumlah user di database minimal harus 3.");
            return null;
        }

        // Menentukan batas bawah (index 2) dan batas atas (ukuran list)
        int minIndex = 2;
        int maxIndex = userTable.size();

        // Formula untuk mendapatkan angka acak di rentang [minIndex, maxIndex - 1]
        java.util.Random random = new java.util.Random();
        
        while(true){
            int randomIndex = random.nextInt(maxIndex - minIndex) + minIndex;
            if(userTable.get(randomIndex).getRole() == UserRole.USER){
                return userTable.get(randomIndex);
            }
        }
    }

    /**
     * Simpan user baru ke database
     * @param user user yang akan disimpan
     * @return true jika berhasil, false jika username sudah ada
     */
    public static boolean save(User user) {
        // Cek username sudah ada atau belum
        if (findByUsername(user.getUsername()) != null) {
            System.out.println("Username '" + user.getUsername() + "' sudah ada di database!");
            return false;
        }
        
        return userTable.add(user);
    }

    /**
     * Update data user yang sudah ada
     * @param user user yang akan diupdate
     * @return true jika berhasil, false jika user tidak ditemukan
     */
    public static boolean update(User user) {
        User existingUser = findByUsername(user.getUsername());
        
        if (existingUser == null) {
            System.out.println("User dengan username '" + user.getUsername() + "' tidak ditemukan!");
            return false;
        }

        // Update semua field
        existingUser.setPassword(user.getPassword());
        existingUser.setProfilname(user.getProfilname());
        existingUser.setKontak(user.getKontak());
        existingUser.setBio(user.getBio());
        existingUser.setStatus(user.getStatus());
        existingUser.setRole(user.getRole());

        System.out.println("User '" + user.getUsername() + "' berhasil diupdate!");
        return true;
    }

    /**
     * Hapus user dari database
     * @param username username user yang akan dihapus
     * @return true jika berhasil, false jika user tidak ditemukan
     */
    public static boolean delete(String username) {
        User user = findByUsername(username);
        
        if (user == null) {
            System.out.println("User dengan username '" + username + "' tidak ditemukan!");
            return false;
        }

        boolean removed = userTable.remove(user);
        
        if (removed) {
            System.out.println("User '" + username + "' berhasil dihapus!");
        }
        
        return removed;
    }

    /**
     * Dapatkan semua user dari database
     * @return List berisi semua user
     */
    public static List<User> getAll() {
        return new ArrayList<>(userTable);
    }

    /**
     * Dapatkan jumlah user di database
     * @return jumlah user
     */
    public static int count() {
        return userTable.size();
    }

    /**
     * Hapus semua user dari database (untuk testing)
     */
    public static void clear() {
        userTable.clear();
    }
}
