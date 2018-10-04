package Utils;

public class Session {
    private static boolean loggedIn = false;

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(boolean loggedIn) {
        Session.loggedIn = loggedIn;
    }
}
