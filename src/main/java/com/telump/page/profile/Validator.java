package com.telump.page.profile;

/**
 * Utility class untuk validasi input pada profile
 * Menyediakan method-method validasi yang reusable
 */
public class Validator {

    /**
     * Validasi apakah string kosong atau null
     * @param input string yang akan divalidasi
     * @return true jika kosong, false jika ada isi
     */
    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    /**
     * Validasi format kontak (email atau nomor telepon)
     * Email: harus mengandung @ dan .
     * Nomor telepon: minimal 9 karakter, hanya angka, spasi, dash, atau +
     * @param kontak kontak yang akan divalidasi
     * @return pesan error jika invalid, null jika valid
     */
    public static String validateKontak(String kontak) {
        if (isEmpty(kontak)) {
            return "Kontak tidak boleh kosong!";
        }

        kontak = kontak.trim();

        // Cek jika format email (mengandung @)
        if (kontak.contains("@")) {
            if (!isValidEmail(kontak)) {
                return "Format email tidak valid! Contoh: user@example.com";
            }
        }
        // Cek jika format nomor telepon (hanya angka, +, -, space)
        else if (!isValidPhoneNumber(kontak)) {
            return "Format nomor telepon tidak valid! Gunakan format: 0812345678 atau +6212345678";
        }

        return null; // Valid
    }

    /**
     * Validasi format email
     * @param email email yang akan divalidasi
     * @return true jika valid, false jika tidak
     */
    private static boolean isValidEmail(String email) {
        // Email harus: ada @, ada ., tidak boleh spasi
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    /**
     * Validasi format nomor telepon
     * @param phoneNumber nomor telepon yang akan divalidasi
     * @return true jika valid, false jika tidak
     */
    private static boolean isValidPhoneNumber(String phoneNumber) {
        // Nomor telepon: minimal 9 karakter, hanya angka, spasi, dash, atau +
        return phoneNumber.matches("^[0-9+\\s-]{9,}$");
    }

    /**
     * Validasi password (minimal 6 karakter)
     * @param password password yang akan divalidasi
     * @return pesan error jika invalid, null jika valid
     */
    public static String validatePassword(String password) {
        if (isEmpty(password)) {
            return "Password tidak boleh kosong!";
        }

        if (password.length() < 6) {
            return "Password minimal 6 karakter!";
        }

        return null; // Valid
    }

    /**
     * Validasi field profilname (nama lengkap)
     * @param profilname nama yang akan divalidasi
     * @return pesan error jika invalid, null jika valid
     */
    public static String validateProfilname(String profilname) {
        if (isEmpty(profilname)) {
            return "Nama lengkap tidak boleh kosong!";
        }

        if (profilname.trim().length() < 3) {
            return "Nama lengkap minimal 3 karakter!";
        }

        return null; // Valid
    }

    /**
     * Validasi field bio
     * @param bio bio yang akan divalidasi
     * @return pesan error jika invalid, null jika valid
     */
    public static String validateBio(String bio) {
        if (isEmpty(bio)) {
            return "Bio tidak boleh kosong!";
        }

        if (bio.trim().length() > 200) {
            return "Bio maksimal 200 karakter!";
        }

        return null; // Valid
    }
}
