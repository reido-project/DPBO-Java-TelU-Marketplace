package com.telump.page.auth;

import com.telump.common.AuthenticationService;
import com.telump.common.State;
import com.telump.common.Util;
import com.telump.page.AppInfo;
import com.telump.user.User;

/**
 * Halaman Register - meminta data user baru untuk registrasi akun
 */
public class RegisterAccount {

    /**
     * Menampilkan form registrasi dan memproses pembuatan akun baru
     * @return AppInfo dengan state Login jika berhasil, atau Start jika gagal
     */
    public AppInfo register(){
        String errorMessage = "";
        State appState = State.RegisterAccount;

        // Minta input data registrasi
        String username = Util.stringInput("Username: ");
        String password = Util.stringInput("Password (minimal 6 karakter): ");
        String profilname = Util.stringInput("Nama Profil: ");
        String kontak = Util.stringInput("Kontak: ");
        String bio = Util.stringInput("Bio: ");

        // Register via AuthenticationService
        User newUser = AuthenticationService.registerUser(username, password, profilname, kontak, bio);

        if (newUser != null) {
            // Registrasi berhasil, arahkan ke Login
            errorMessage = "Registrasi berhasil! Silakan login.";
            appState = State.Login;
        } else {
            // Registrasi gagal, tampilkan pesan error spesifik
            errorMessage = "Registrasi gagal: " + AuthenticationService.getLastError();
            appState = State.Start;
        }

        return new AppInfo(errorMessage, appState);
    }
}