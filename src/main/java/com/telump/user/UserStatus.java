package com.telump.user;

public enum UserStatus {
    ACTIVE("Aktif"),
    DELETED("Dihapus"),
    BANNED("Dibanned");

    private final String displayName;

    UserStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
