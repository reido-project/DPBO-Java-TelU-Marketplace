package com.telump.page.auth;

import com.telump.common.State;
import com.telump.user.User;

public class Login {
    public LoginInfo login(){
        String errorMessage = null;
        State appState = State.Login;
        User user = null;

        return new LoginInfo(errorMessage, appState, user);
    }
}
