package com.telump.user;

import java.util.Arrays;
import java.util.List;

/**
 * Class Admin - user administrator yang extend dari User
 * Memiliki permissions lengkap untuk mengelola platform
 */
public class Admin extends User {
    
    public Admin(String username, String password, String profilname, String kontak, String bio) {
        super(username, password, profilname, kontak, bio, UserRole.ADMIN);
    }

    @Override
    public String displayRole() {
        return "Administrator";
    }

    @Override
    public List<String> getPermissions() {
        return Arrays.asList(
            "view_catalog",
            "post_ads",
            "post_items",
            "leave_reviews",
            "edit_own_profile",
            "message_users",
            "moderate_content",
            "ban_users",
            "delete_items",
            "delete_ads",
            "view_analytics",
            "manage_admins",
            "system_settings"
        );
    }
}
