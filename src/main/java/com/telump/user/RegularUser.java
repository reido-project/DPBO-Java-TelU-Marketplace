package com.telump.user;

import java.util.Arrays;
import java.util.List;

public class RegularUser extends User {
    
    public RegularUser(String username, String password, String profilname, String kontak, String bio) {
        super(username, password, profilname, kontak, bio, UserRole.USER);
    }

    @Override
    public String displayRole() {
        return "Regular User";
    }

    @Override
    public List<String> getPermissions() {
        return Arrays.asList(
            "view_catalog",
            "post_ads",
            "post_items",
            "leave_reviews",
            "edit_own_profile",
            "message_users"
        );
    }
}
