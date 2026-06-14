package com.telump;

import com.telump.common.State;
import com.telump.common.Util;
import com.telump.page.AppInfo;
import com.telump.page.Start;
import com.telump.page.admin.AdminDashboard;
import com.telump.page.auth.Login;
import com.telump.page.auth.LoginInfo;
import com.telump.page.auth.RegisterAccount;
import com.telump.page.dashboard.Dashboard;
import com.telump.user.User;

public class AppController {
    public State appState;
    public static User session = null;
    public String errorMessage; 

    public void appController() {
        String welcome = "Welcome to Tel-U Marketplace!";

        Start startPage = new Start();
        Login loginPage = new Login();
        RegisterAccount registerAccountPage = new RegisterAccount();
        Dashboard dashboardPage = new Dashboard();
        AdminDashboard adminDashboardPage = new AdminDashboard();

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
            } else {
                // Sinkronkan session dengan AuthenticationService (misal setelah logout)
                session = com.telump.common.AuthenticationService.getCurrentUser();
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
                    // Cek role user yang sedang login, redirect ke menu yang sesuai
                    User currentUser = com.telump.common.AuthenticationService.getCurrentUser();
                    if (currentUser != null && currentUser.isAdmin()) {
                        // User adalah ADMIN, redirect ke AdminDashboard
                        rf = adminDashboardPage.adminDashboard();
                    } else {
                        // User biasa, ke regular Dashboard
                        rf = dashboardPage.dashboard();
                    }
                    break;
                default:
                    return;
            }
        }
    }
    public static User getSession(){
        return session;
    }
}