package Model;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.*;
import java.util.ArrayList;

public class Alat extends Model {

    private static final String JSON_PATH = "database\\alats.json";

    private Integer id;
    private String nama;
    private Integer kuantitas;
    private String deskripsi;

    public Alat(String nama, Integer kuantitas, String deskripsi) {
        this.nama = nama;
        this.kuantitas = kuantitas;
        this.deskripsi = deskripsi;
        this.id = getLastId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(Integer kuantitas) {
        this.kuantitas = kuantitas;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    @Override
    public String toString() {
        return "Alat{" + "id=" + id + ", nama=" + nama + ", kuantitas=" + kuantitas + ", deskripsi=" + deskripsi + '}';
    }

    private static int getLastId() {
        JsonReader reader = getReader(JSON_PATH);
        Alat[] alats = gson.fromJson(reader, Alat[].class);
        if (alats == null) {
            return 1;
        } else {
            return (alats[alats.length - 1].getId()) + 1;
        }
    }


    private static void writeToJson(ArrayList<Alat> bahans) {
        String jsonString = gson.toJson(bahans);

        FileWriter writer = getWriter(JSON_PATH);
        try {
            writer.write(jsonString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void store(Alat alat){

        BufferedReader buffer = null;
        try {
            buffer = new BufferedReader(new FileReader(JSON_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (buffer.readLine() == null) {
                ArrayList<Alat> bahans_2 = new ArrayList<Alat>();
                bahans_2.add(alat);
                writeToJson(bahans_2);
            } else {
                JsonReader reader = getReader(JSON_PATH);
                ArrayList<Alat> alats = gson.fromJson(reader, new TypeToken<ArrayList<Alat>>() {
                }.getType());
                alats.add(alat);
                writeToJson(alats);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList getAll() {
        BufferedReader buffer = null;
        ArrayList<Alat> alats = null;

        try {
            buffer = new BufferedReader(new FileReader(JSON_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (buffer.readLine() == null) {
                return alats;
            } else {
                JsonReader reader = getReader(JSON_PATH);
                alats = gson.fromJson(reader, new TypeToken<ArrayList<Alat>>() {
                }.getType());

                return alats;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void update(Alat alat) {
        ArrayList<Alat> alats = getAll();

        int index = 0;

        for (Alat alt : alats) {
            if (alt.getId() == alat.getId()) {
                alats.set(index, alat);
                break;
            }
            index++;
        }

        writeToJson(alats);
    }

    public static void delete(int id) {
        ArrayList<Alat> alats = getAll();

        int index = 0;
        for (Alat alt : alats) {
            if (alt.getId() == id) {
                alats.remove(index);
                break;
            }
            index++;
        }

        writeToJson(alats);
    }

    public static Alat findId(int id) {
        ArrayList<Alat> alats = getAll();

        for (Alat alat : alats) {
            if (alat.getId() == id) {
                return alat;
            }
        }

        return null;
    }

}
