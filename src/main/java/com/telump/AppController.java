package com.telump;

import com.telump.common.Util;
import com.telump.page.*;
import com.telump.page.auth.Login;
import com.telump.page.auth.RegisterAccount;
import com.telump.page.dashboard.Dashboard;
import com.telump.user.User;
import com.telump.common.State;

public class AppController {
    public static State currentState = State.Start;

    public static User currentSession = null;

    public static String errorMessage = "Welcome to Tel-U Marketplace!";

    public static void appController() {
        while (true) {
            System.out.println();
            System.out.println(errorMessage);
            switch (currentState) {
                case State.Start:
                    Start.start();
                    break;
                case State.Login:
                    if (!Util.confirmation("Continue to Login? (y/n): ")) {
                        AppController.currentState = State.Start;
                        errorMessage = "Welcome to Tel-U Marketplace!";
                        break;
                    }
                    Login.login();
                    break;
                case State.RegisterAccount:
                    if (!Util.confirmation("Continue to Registration? (y/n): ")) {
                        AppController.currentState = State.Start;
                        errorMessage = "Welcome to Tel-U Marketplace!";
                        break;
                    }
                    errorMessage = RegisterAccount.register();
                    break;
                case State.Dashboard:
                    Dashboard.dashboard();
                    break;
                default:
                    return;
            }
        }
    }
}