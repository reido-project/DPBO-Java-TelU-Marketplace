package com.telump.common;



    // dimplementasikan user dan subclassnya
 
public interface Authenticatable {
    // login
    boolean login(String username, String password);

    //  Logout user
    void logout();
}
