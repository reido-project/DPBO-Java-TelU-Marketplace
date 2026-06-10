package com.telump.common;

/**
 * Interface untuk autentikasi user (login/logout)
 * Diimplementasikan oleh User dan subclassnya
 */
public interface Authenticatable {
    boolean login(String username, String password);

    /**
     * Logout user
     */
    void logout();
}
