package Controller;

import Model.Admin;
import Utils.Session;
import View.AuthenticationView;

public class AuthenticationController {

    public static void login(){
        AuthenticationView.showLoginForm();
    }

    public static void attemptLogin(String username, String password){
        final boolean check = Admin.checkCredential(username, password);

        if(check){
            Session.setLoggedIn(true);
        }

        HomeController.getHomeMenu();
    }

    public static void logout(){
        Session.setLoggedIn(false);
        HomeController.getHomeMenu();
    }
}
