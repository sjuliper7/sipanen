package Model;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;

public class Admin{
    
    private static final String JSON_PATH = "database\\users.json";
    
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    private static Gson gson = new Gson();
    
    private static JsonReader getAllData() {
        try {
            JsonReader reader = new JsonReader(new FileReader(JSON_PATH));
            return reader;
        } catch(Exception e){
            System.err.println(e);
        }
        
        return null;
    }
    
    public static boolean checkCredential(String username, String password){
        JsonReader reader = getAllData();
        Admin[] admins = gson.fromJson(reader, Admin[].class);

        for (Admin admin: admins) {
            if(admin.getPassword().equals(password) && admin.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }
}
