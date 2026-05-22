package com.telump;

import com.telump.common.Util;
import com.telump.page.*;
import com.telump.page.auth.Login;
import com.telump.page.auth.LoginInfo;
import com.telump.page.auth.RegisterAccount;
import com.telump.page.dashboard.Dashboard;
import com.telump.user.User;
import com.telump.common.State;

public class AppController {
    public State appState;
    public User session = null;
    public String errorMessage;

    public void appController() {
        String welcome = "Welcome to Tel-U Marketplace!";

        Start startPage = new Start();
        Login loginPage = new Login();
        RegisterAccount registerAccountPage = new RegisterAccount();
        Dashboard dashboardPage = new Dashboard();

        AppInfo rf = null;

        while (true) {
            if(rf == null){
                rf = new AppInfo(welcome, State.Start);
            }
            else if(rf.getErrorMessage() == null || rf.getErrorMessage().isEmpty()){
                rf.setErrorMessage(welcome);
            }

            appState = rf.getAppState();
            errorMessage = rf.getErrorMessage();
            if(rf instanceof LoginInfo){
                session = ((LoginInfo) rf).getUser();
            }

            System.out.println("\n" + errorMessage);

            switch (appState) {
                case State.Start:
                    rf = startPage.start();
                    break;
                case State.Login:
                    if (!Util.confirmation("Continue to Login? (y/n): ")) {
                        appState = State.Start;
                        break;
                    }
                    rf = loginPage.login();
                    break;
                case State.RegisterAccount:
                    if (!Util.confirmation("Continue to Registration? (y/n): ")) {
                        appState = State.Start;
                        break;
                    }
                    rf = registerAccountPage.register();
                    break;
                case State.Dashboard:
                    rf = dashboardPage.dashboard();
                    break;
                default:
                    return;
            }
        }
    }
}