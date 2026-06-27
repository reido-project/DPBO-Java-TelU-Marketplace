package com.telump.page.admin;

import com.telump.common.AuthenticationService;
import com.telump.common.State;
import com.telump.common.Util;
import com.telump.database.UserTable;
import com.telump.page.AppInfo;
import com.telump.user.User;

/**
 * Dashboard khusus untuk Admin
 * Menu terbatas untuk user dengan role ADMIN
 * Fitur: manage user, view semua user, dll
 */
public class AdminDashboard {
    public AppInfo adminDashboard() {
        User currentUser = AuthenticationService.getCurrentUser();

        // Security check - pastikan user yang login adalah ADMIN
        if (currentUser == null || !currentUser.isAdmin()) {
            System.out.println("\n[Error] Akses ditolak! Hanya ADMIN yang bisa mengakses menu ini.");
            Util.stringInput("Tekan Enter untuk kembali...");
            return new AppInfo("Akses admin ditolak!", State.Dashboard);
        }

        while (true) {
            Util.header("ADMIN DASHBOARD");
            System.out.println("Selamat datang, " + currentUser.getUsername() + " (ADMIN)");
            System.out.println("\n1. Lihat Semua User");
            System.out.println("2. Search User");
            System.out.println("3. Hapus User");
            System.out.println("4. View Profile Saya");
            System.out.println("5. Edit Profile Saya");
            System.out.println("9. Logout");
            System.out.println("0. Exit");

            int pilihan = Util.integerInput("Pilih opsi: ");

            switch (pilihan) {
                case 1:
                    showAllUsers();
                    break;
                case 2:
                    searchUser();
                    break;
                case 3:
                    deleteUser();
                    break;
                case 4:
                    com.telump.page.profile.Profile.showProfile();
                    break;
                case 5:
                    com.telump.page.profile.UpdateProfile.showUpdateProfileMenu();
                    break;
                case 9:
                    AuthenticationService.logout();
                    return new AppInfo("Logout berhasil!", State.Start);
                case 0:
                    return new AppInfo(null, State.Exit);
                default:
                    System.out.println("[Error] Opsi tidak valid!");
            }
        }
    }

    /**
     * Tampilkan semua user yang terdaftar (admin only)
     */
    private void showAllUsers() {
        System.out.println("\n========== DAFTAR SEMUA USER ==========");
        var allUsers = UserTable.getAll();

        if (allUsers.isEmpty()) {
            System.out.println("Belum ada user yang terdaftar.");
        } else {
            System.out.println(String.format("%-20s %-30s %-15s %-10s", "Username", "Nama Lengkap", "Role", "Status"));
            System.out.println("-----------------------------------------------------------------------");

            for (User user : allUsers) {
                System.out.println(String.format(
                    "%-20s %-30s %-15s %-10s",
                    user.getUsername(),
                    (user.getProfilname().isEmpty() ? "-" : user.getProfilname()),
                    user.getRole().getDisplayName(),
                    user.getStatus()
                ));
            }

            System.out.println("-----------------------------------------------------------------------");
            System.out.println("Total user: " + allUsers.size());
        }

        Util.stringInput("\nTekan Enter untuk kembali...");
    }

    /**
     * Search user berdasarkan username
     */
    private void searchUser() {
        System.out.println("\n--- Search User ---");
        String username = Util.stringInput("Masukkan username yang dicari: ");

        User foundUser = UserTable.findByUsername(username);

        if (foundUser != null) {
            System.out.println("\n========== HASIL PENCARIAN ==========");
            System.out.println("Username      : " + foundUser.getUsername());
            System.out.println("Nama Lengkap  : " + (foundUser.getProfilname().isEmpty() ? "-" : foundUser.getProfilname()));
            System.out.println("Kontak        : " + (foundUser.getKontak().isEmpty() ? "-" : foundUser.getKontak()));
            System.out.println("Role          : " + foundUser.getRole().getDisplayName());
            System.out.println("Status        : " + foundUser.getStatus());
            System.out.println("Bio           : " + (foundUser.getBio().isEmpty() ? "-" : foundUser.getBio()));
            System.out.println("====================================");
        } else {
            System.out.println("\n[Info] User dengan username '" + username + "' tidak ditemukan.");
        }

        Util.stringInput("\nTekan Enter untuk kembali...");
    }

    /**
     * Hapus user (admin only)
     */
    private void deleteUser() {
        System.out.println("\n--- Hapus User ---");
        String username = Util.stringInput("Masukkan username yang ingin dihapus: ");

        // Cegah admin menghapus dirinya sendiri
        User currentUser = AuthenticationService.getCurrentUser();
        if (username.equals(currentUser.getUsername())) {
            System.out.println("[Error] Anda tidak bisa menghapus akun Anda sendiri!");
            Util.stringInput("Tekan Enter untuk kembali...");
            return;
        }

        User userToDelete = UserTable.findByUsername(username);
        if (userToDelete == null) {
            System.out.println("[Error] User dengan username '" + username + "' tidak ditemukan.");
            Util.stringInput("Tekan Enter untuk kembali...");
            return;
        }

        System.out.println("\nUser yang akan dihapus:");
        System.out.println("- Username: " + userToDelete.getUsername());
        System.out.println("- Nama: " + userToDelete.getProfilname());
        System.out.println("- Role: " + userToDelete.getRole().getDisplayName());

        if (!Util.confirmation("\nApakah Anda yakin ingin menghapus user ini? (y/n): ")) {
            System.out.println("Pembatalan penghapusan user.");
            return;
        }

        if (UserTable.delete(username)) {
            System.out.println("[Sukses] User berhasil dihapus!");
        } else {
            System.out.println("[Error] Gagal menghapus user.");
        }

        Util.stringInput("Tekan Enter untuk kembali...");
    }
}
