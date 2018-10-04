package Controller;

import Utils.Session;
import View.HomeView;

public class HomeController {

    public static void getHomeMenu(){
        if(Session.isLoggedIn()){
            HomeView.showDashboard();
        } else{
            HomeView.showHomeMenu();
        }
    }
}
