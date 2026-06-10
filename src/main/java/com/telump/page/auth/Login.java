package com.telump.page.auth;

import com.telump.common.AuthenticationService;
import com.telump.common.State;
import com.telump.common.Util;
import com.telump.user.User;

/**
 * Halaman Login - meminta input username dan password dari user
 */
public class Login {

    /**
     * Menampilkan form login dan memproses autentikasi
     * @return LoginInfo dengan sesi user jika berhasil, atau error message jika gagal
     */
    public LoginInfo login(){
        String errorMessage = null;
        State appState = State.Login;
        User user = null;

        // Minta input username dan password
        String username = Util.stringInput("Username: ");
        String password = Util.stringInput("Password: ");

        // Autentikasi via AuthenticationService
        user = AuthenticationService.login(username, password);

        if (user != null) {
            // Login berhasil, arahkan ke Dashboard
            errorMessage = "Selamat datang, " + user.getUsername() + "!";
            appState = State.Dashboard;
        } else {
            // Login gagal, tampilkan pesan error spesifik
            errorMessage = AuthenticationService.getLastError();
            appState = State.Start;
        }

        return new LoginInfo(errorMessage, appState, user);
    }
}
