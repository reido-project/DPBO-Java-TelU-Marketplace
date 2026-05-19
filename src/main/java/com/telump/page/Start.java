package com.telump.page;

import com.telump.AppController;
import com.telump.common.Util;
import com.telump.common.State;

public class Start {
    public static void start(){
        AppController.errorMessage = "Welcome to Tel-U Marketplace!";
        System.out.print("""
                1. Login
                2. Register
                0. Exit
                """);
        switch(Util.integerInput("Pilih opsi: ")) {
            case 1:
                AppController.currentState = State.Login;
                break;
            case 2:
                AppController.currentState = State.Register;
                break;
            case 0:
                AppController.currentState = State.Exit;
        }
    }
}

