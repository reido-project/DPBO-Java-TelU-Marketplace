package com.telump.page.auth;

import com.telump.common.AuthenticationService;
import com.telump.common.State;
import com.telump.page.AppInfo;
import com.telump.user.User;

/**
 * LoginInfo class untuk menyimpan info login user
 * Mengintegrasikan AuthenticationService untuk validasi login
 */
public class LoginInfo extends AppInfo {
    private User user;

    public LoginInfo(String errorMessage, State appState, User user) {
        super(errorMessage, appState);
        this.user = user;
    }

    /**
     * Autentikasi user dengan username dan password
     * @param username username user
     * @param password password user
     * @return true jika autentikasi berhasil
     */
    public boolean authenticateUser(String username, String password) {
        User authenticatedUser = AuthenticationService.authenticateUser(username, password);
        
        if (authenticatedUser != null) {
            this.user = authenticatedUser;
            setErrorMessage(null);
            return true;
        }
        
        this.user = null;
        setErrorMessage("Username atau password salah!");
        return false;
    }

    /**
     * Validasi username unik untuk registrasi
     * @param username username yang akan dicek
     * @return true jika username unik
     */
    public boolean isUsernameAvailable(String username) {
        return AuthenticationService.isUsernameUnique(username);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Cek apakah user sudah login
     * @return true jika user tidak null
     */
    public boolean isLoggedIn() {
        return user != null;
    }

    /**
     * Logout user
     */
    public void logout() {
        if (user != null) {
            AuthenticationService.logout();
            user = null;
            setErrorMessage("Logout berhasil!");
        }
    }
}
