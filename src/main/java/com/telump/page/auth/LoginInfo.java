package com.telump.page.auth;

import com.telump.common.State;
import com.telump.page.AppInfo;
import com.telump.user.User;

public class LoginInfo extends AppInfo {
    private User user;

    public LoginInfo(String errorMessage, State appState, User user) {
        super(errorMessage, appState);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
