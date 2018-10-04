package Model;

import static Model.Model.gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Panen extends Model {

    private static final String JSON_PATH = "database\\panens.json";
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    private int id;
    private Member member;
    private ArrayList<DetailPanen> detailPanens;
    private String tanggalLapor;

    public Panen(Member member, ArrayList<DetailPanen> detailPanens) {
        this.member = member;
        this.detailPanens = detailPanens;
        Date date = new Date();
        this.tanggalLapor = sdf.format(date);
        this.id = getLastId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public ArrayList<DetailPanen> getDetailPanens() {
        return detailPanens;
    }

    public void setDetailPanens(ArrayList<DetailPanen> detailPanens) {
        this.detailPanens = detailPanens;
    }

    public String getTanggalLapor() {
        return tanggalLapor;
    }

    public void setTanggalLapor(String tanggalLapor) {
        this.tanggalLapor = tanggalLapor;
    }

    private static int getLastId() {
        JsonReader reader = getReader(JSON_PATH);
        Peminjaman[] peminjamans = gson.fromJson(reader, Peminjaman[].class);
        if (peminjamans == null) {
            return 1;
        } else {
            return (peminjamans[peminjamans.length - 1].getId()) + 1;
        }

    }

    private static void writeToJson(ArrayList<Panen> panens) {
        String jsonString = gson.toJson(panens);

        FileWriter writer = getWriter(JSON_PATH);
        try {
            writer.write(jsonString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList getAll() {
        BufferedReader buffer = null;
        ArrayList<Panen> panens = null;

        try {
            buffer = new BufferedReader(new FileReader(JSON_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (buffer.readLine() == null) {
                return panens;
            } else {
                JsonReader reader = getReader(JSON_PATH);
                panens = gson.fromJson(reader, new TypeToken<ArrayList<Panen>>() {
                }.getType());

                return panens;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Boolean store(Panen panen) {
        JsonReader reader = getReader(JSON_PATH);
        ArrayList<Panen> panens = gson.fromJson(reader, new TypeToken<ArrayList<Panen>>() {
        }.getType());
        panen.setDetailPanens(null);
        if (panens == null) {
            ArrayList<Panen> panens1 = new ArrayList<Panen>();
            panens1.add(panen);
            writeToJson(panens1);
            return true;
        } else {
            panens.add(panen);
            writeToJson(panens);
            return true;
        }

    }

    public static void update(Panen panenUpdate) {
        ArrayList<Panen> panens = getAll();

        int index = 0;

        for (Panen panen : panens) {
            if (panen.getId() == panenUpdate.getId()) {
                panens.set(index, panenUpdate);
                break;
            }
            index++;
        }

        writeToJson(panens);
    }

    public static Panen find(int id) {
        try {
            ArrayList<Panen> panens = getAll();
            for (Panen panen : panens) {
                if (panen.getId() == id) {
                    return panen;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static void delete(Integer id) {
        ArrayList<Panen> panens = getAll();
        int index = 0;

        for (Panen panen : panens) {
            if (panen.getId() == id) {
                panens.remove(index);
                break;
            }
            index++;
        }
        writeToJson(panens);
    }

}
