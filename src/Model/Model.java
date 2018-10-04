package Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

abstract public class Model {

    protected static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected static JsonReader getReader(String JSON_PATH) {
        try {
            JsonReader reader = new JsonReader(new FileReader(JSON_PATH));
            return reader;
        } catch (Exception e) {
            System.err.println(e);
        }

        return null;
    }

    protected static FileWriter getWriter(String JSON_PATH) {

        try {
            FileWriter writer = new FileWriter(JSON_PATH);
            return writer;
        } catch (IOException ex) {
            System.err.println(ex);
        }

        return null;
    }
}
