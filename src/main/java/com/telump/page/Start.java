package com.telump.page;

import com.telump.common.Util;
import com.telump.common.State;

public class Start {
    public AppInfo start(){
        String errorMessage = null;
        State appState = State.Start;

        System.out.print("""
                1. Login
                2. Register
                0. Exit
                """);
        appState = switch (Util.integerInput("Pilih opsi: ")) {
            case 1 -> {
                errorMessage = "Please Enter Username and Password.";
                yield State.Login;
            }
            case 2 -> {
                errorMessage = "Please Enter Account Info.";
                yield State.RegisterAccount;
            }
            case 0 -> {
                errorMessage = "Bye~";
                yield State.Exit;
            }
            default -> appState;
        };

        return new AppInfo(errorMessage, appState);
    }
}

