package com.telump.page.profile;

import com.telump.common.AuthenticationService;
import com.telump.common.Util;
import com.telump.database.UserTable;
import com.telump.user.User;

/**
 * Class untuk handle update profile user
 * Fitur: update nama, email/kontak, bio, dan password dengan validasi lengkap
 */
public class UpdateProfile {

    /**
     * Tampilkan menu update profile dan handle proses update
     * User bisa memilih field mana yang ingin diupdate
     */
    public static void showUpdateProfileMenu() {
        User user = AuthenticationService.getCurrentUser();
        
        if (user == null) {
            System.out.println("\n[Error] Tidak ada user yang sedang login.");
            Util.stringInput("Tekan Enter untuk kembali...");
            return;
        }

        while (true) {
            System.out.println("\n========== EDIT PROFIL ==========");
            System.out.println("Pilih field yang ingin diupdate:");
            System.out.println("1. Nama Lengkap");
            System.out.println("2. Kontak (Email/Telepon)");
            System.out.println("3. Bio");
            System.out.println("4. Password");
            System.out.println("0. Kembali");
            System.out.println("=================================");

            int pilihan = Util.integerInput("Pilih opsi: ");

            switch (pilihan) {
                case 1:
                    updateProfilname(user);
                    break;
                case 2:
                    updateKontak(user);
                    break;
                case 3:
                    updateBio(user);
                    break;
                case 4:
                    updatePassword(user);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("[Error] Opsi tidak valid!");
            }
        }
    }

    /**
     * Update nama lengkap user
     * @param user user yang sedang login
     */
    private static void updateProfilname(User user) {
        System.out.println("\n--- Update Nama Lengkap ---");
        System.out.println("Nama lengkap saat ini: " + user.getProfilname());

        String newProfilname = Util.stringInput("Masukkan nama lengkap baru: ");

        // Validasi input
        String validationError = Validator.validateProfilname(newProfilname);
        if (validationError != null) {
            System.out.println("[Error] " + validationError);
            Util.stringInput("Tekan Enter untuk kembali...");
            return;
        }

        // Konfirmasi update
        if (!Util.confirmation("Apakah Anda yakin ingin mengubah nama lengkap? (y/n): ")) {
            System.out.println("Pembatalan update nama lengkap.");
            return;
        }

        // Update data user
        user.setProfilname(newProfilname);
        if (UserTable.update(user)) {
            System.out.println("[Sukses] Nama lengkap berhasil diupdate!");
        } else {
            System.out.println("[Error] Gagal mengupdate nama lengkap.");
        }

        Util.stringInput("Tekan Enter untuk kembali...");
    }

    /**
     * Update kontak (email atau nomor telepon) user
     * @param user user yang sedang login
     */
    private static void updateKontak(User user) {
        System.out.println("\n--- Update Kontak ---");
        System.out.println("Kontak saat ini: " + (user.getKontak().isEmpty() ? "-" : user.getKontak()));

        String newKontak = Util.stringInput("Masukkan kontak baru (email/telepon): ");

        // Validasi input
        String validationError = Validator.validateKontak(newKontak);
        if (validationError != null) {
            System.out.println("[Error] " + validationError);
            Util.stringInput("Tekan Enter untuk kembali...");
            return;
        }

        // Konfirmasi update
        if (!Util.confirmation("Apakah Anda yakin ingin mengubah kontak? (y/n): ")) {
            System.out.println("Pembatalan update kontak.");
            return;
        }

        // Update data user
        user.setKontak(newKontak);
        if (UserTable.update(user)) {
            System.out.println("[Sukses] Kontak berhasil diupdate!");
        } else {
            System.out.println("[Error] Gagal mengupdate kontak.");
        }

        Util.stringInput("Tekan Enter untuk kembali...");
    }

    /**
     * Update bio user
     * @param user user yang sedang login
     */
    private static void updateBio(User user) {
        System.out.println("\n--- Update Bio ---");
        System.out.println("Bio saat ini: " + (user.getBio().isEmpty() ? "-" : user.getBio()));

        String newBio = Util.stringInput("Masukkan bio baru (kosongkan untuk hapus): ");

        // Jika kosong, set sebagai empty string (opsional)
        if (newBio.trim().isEmpty()) {
            newBio = "";
        }

        // Validasi input jika tidak kosong
        if (!newBio.isEmpty()) {
            String validationError = Validator.validateBio(newBio);
            if (validationError != null) {
                System.out.println("[Error] " + validationError);
                Util.stringInput("Tekan Enter untuk kembali...");
                return;
            }
        }

        // Konfirmasi update
        if (!Util.confirmation("Apakah Anda yakin ingin mengubah bio? (y/n): ")) {
            System.out.println("Pembatalan update bio.");
            return;
        }

        // Update data user
        user.setBio(newBio);
        if (UserTable.update(user)) {
            System.out.println("[Sukses] Bio berhasil diupdate!");
        } else {
            System.out.println("[Error] Gagal mengupdate bio.");
        }

        Util.stringInput("Tekan Enter untuk kembali...");
    }

    /**
     * Update password user
     * Meminta konfirmasi password lama sebelum ganti password baru
     * @param user user yang sedang login
     */
    private static void updatePassword(User user) {
        System.out.println("\n--- Update Password ---");

        // Konfirmasi password lama
        String oldPassword = Util.passwordInput("Masukkan password lama: ");

        // Validasi password lama
        if (!user.getPassword().equals(oldPassword)) {
            System.out.println("[Error] Password lama tidak sesuai!");
            Util.stringInput("Tekan Enter untuk kembali...");
            return;
        }

        // Input password baru
        String newPassword = Util.passwordInput("Masukkan password baru: ");

        // Validasi password baru
        String validationError = Validator.validatePassword(newPassword);
        if (validationError != null) {
            System.out.println("[Error] " + validationError);
            Util.stringInput("Tekan Enter untuk kembali...");
            return;
        }

        // Cek password baru tidak sama dengan password lama
        if (newPassword.equals(oldPassword)) {
            System.out.println("[Error] Password baru tidak boleh sama dengan password lama!");
            Util.stringInput("Tekan Enter untuk kembali...");
            return;
        }

        // Konfirmasi password baru
        String confirmPassword = Util.passwordInput("Konfirmasi password baru: ");

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("[Error] Konfirmasi password tidak sesuai!");
            Util.stringInput("Tekan Enter untuk kembali...");
            return;
        }

        // Update password
        user.setPassword(newPassword);
        if (UserTable.update(user)) {
            System.out.println("[Sukses] Password berhasil diupdate!");
        } else {
            System.out.println("[Error] Gagal mengupdate password.");
        }

        Util.stringInput("Tekan Enter untuk kembali...");
    }
}
