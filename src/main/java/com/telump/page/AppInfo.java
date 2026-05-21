package com.telump.page;

import com.telump.common.State;

public class AppInfo {
    private String errorMessage;
    private final State appState;

    public AppInfo(String errorMessage, State appState) {
        this.errorMessage = errorMessage;
        this.appState = appState;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public State getAppState() {
        return appState;
    }
}
