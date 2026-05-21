package com.telump.page.auth;

import com.telump.common.State;
import com.telump.page.AppInfo;

public class RegisterAccount {
    public AppInfo register(){
        String errorMessage = "";
        State appState = State.RegisterAccount;

        return new AppInfo(errorMessage, appState);
    }
}