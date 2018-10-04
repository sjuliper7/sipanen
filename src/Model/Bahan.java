package Model;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.*;
import java.util.ArrayList;

public class Bahan extends Model {

    private static final String JSON_PATH = "database\\bahans.json";

    private int id;
    private String nama;
    private Integer kuantitas;
    private String deskripsi;
    private Double harga;

    public Bahan(String nama, Integer kuantitas, String deskripsi, Double harga) {
        this.nama = nama;
        this.kuantitas = kuantitas;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.id = getLastId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Double getHarga() {
        return harga;
    }

    public void setHarga(Double harga) {
        this.harga = harga;
    }

    @Override
    public String toString() {
        return "Bahan{" + "id=" + id + ", nama=" + nama + ", kuantitas=" + kuantitas + ", deskripsi=" + deskripsi + '}';
    }

    private static int getLastId() {
        JsonReader reader = getReader(JSON_PATH);
        Bahan[] bahans = gson.fromJson(reader, Bahan[].class);
        if (bahans == null) {
            return 1;
        } else {
            return (bahans[bahans.length - 1].getId()) + 1;
        }

    }

    private static void writeToJson(ArrayList<Bahan> bahans) {
        String jsonString = gson.toJson(bahans);

        FileWriter writer = getWriter(JSON_PATH);
        try {
            writer.write(jsonString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void store(Bahan bahan) {

        BufferedReader buffer = null;
        try {
            buffer = new BufferedReader(new FileReader(JSON_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (buffer.readLine() == null) {
                ArrayList<Bahan> bahans_2 = new ArrayList<Bahan>();
                bahans_2.add(bahan);
                writeToJson(bahans_2);
            } else {
                JsonReader reader = getReader(JSON_PATH);
                ArrayList<Bahan> bahans = gson.fromJson(reader, new TypeToken<ArrayList<Bahan>>() {
                }.getType());
                bahans.add(bahan);
                writeToJson(bahans);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList getAll() {
        JsonReader reader = getReader(JSON_PATH);
        ArrayList<Bahan> bahans = gson.fromJson(reader, new TypeToken<ArrayList<Bahan>>() {
        }.getType());

        return bahans;

    }

    public static void update(Bahan bahan) {
        ArrayList<Bahan> bahans = getAll();

        int index = 0;

        for (Bahan bhn : bahans) {
            if (bhn.getId() == bahan.getId()) {
                bahans.set(index, bahan);
                break;
            }
            index++;
        }

        writeToJson(bahans);
    }

    public static void delete(int id) {
        ArrayList<Bahan> bahans = getAll();

        int index = 0;
        for (Bahan bahan : bahans) {
            if (bahan.getId() == id) {
                bahans.remove(index);
                break;
            }
            index++;
        }

        writeToJson(bahans);
    }

    public static Bahan findId(int id) {
        ArrayList<Bahan> bahans = getAll();

        for (Bahan bahan : bahans) {
            if (bahan.getId() == id) {
                return bahan;
            }
        }

        return null;
    }
}
