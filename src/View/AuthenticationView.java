package View;

import Controller.AuthenticationController;

public class AuthenticationView extends View {

    public static void showLoginForm(){
        System.out.println("Please fill your credential!");
        System.out.print("Username: ");
        String username = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();

        AuthenticationController.attemptLogin(username, password);
    }
}
