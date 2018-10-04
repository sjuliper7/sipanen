package Model;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Komoditas extends Model {

    private static final String JSON_PATH = "database\\komoditas.json";

    private String name;
    private int id;
    private String satuan;

    public Komoditas(String name, String satuan) {
        this.name = name;
        this.satuan = satuan;
        this.id = getLastId();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    private static int getLastId() {
        JsonReader reader = getReader(JSON_PATH);
        Komoditas[] komoditas = gson.fromJson(reader, Komoditas[].class);
        if (komoditas == null) {
            return 1;
        } else {
            return (komoditas[komoditas.length - 1].getId()) + 1;
        }

    }

    public static void create(String name, String satuan) {
        Komoditas k = new Komoditas(name, satuan);

        JsonReader reader = getReader(JSON_PATH);
        ArrayList<Komoditas> komoditas = gson.fromJson(reader, new TypeToken<ArrayList<Komoditas>>() {
        }.getType());

        if (komoditas == null) {
            ArrayList<Komoditas> komoditases = new ArrayList<Komoditas>();
            komoditases.add(k);
            writeToJson(komoditases);
        } else {
            komoditas.add(k);
            writeToJson(komoditas);
        }
    }

    public static ArrayList<Komoditas> getAllData() {
        JsonReader reader = getReader(JSON_PATH);
        ArrayList<Komoditas> komoditas = gson.fromJson(reader, new TypeToken<ArrayList<Komoditas>>() {
        }.getType());

        return komoditas;
    }

    public static Komoditas getOne(int id) {
        ArrayList<Komoditas> komoditas = getAllData();

        for (Komoditas k : komoditas) {
            if (k.getId() == id) {
                return k;
            }
        }

        return null;
    }

    public static void updateOne(Komoditas editedKomoditas) {
        ArrayList<Komoditas> komoditas = getAllData();

        int index = 0;
        for (Komoditas k : komoditas) {
            if (k.getId() == editedKomoditas.getId()) {
                komoditas.set(index, editedKomoditas);
                break;
            }
            index++;
        }

        writeToJson(komoditas);
    }

    public static void delete(int id) {
        ArrayList<Komoditas> komoditas = getAllData();

        int index = 0;
        for (Komoditas k : komoditas) {
            if (k.getId() == id) {
                komoditas.remove(index);
                break;
            }
            index++;
        }

        writeToJson(komoditas);
    }

    private static void writeToJson(ArrayList<Komoditas> komoditas) {
        String jsonString = gson.toJson(komoditas);

        FileWriter writer = getWriter(JSON_PATH);
        try {
            writer.write(jsonString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
