package Model;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.util.Pair;
import java.util.ArrayList;

public class DetailPeminjaman extends Model {

    private static final String JSON_PATH = "database\\detailPeminjamans.json";

    private int id;
    private int idPeminjaman;
    private Alat alat;
    private Integer jumlah;

    public DetailPeminjaman(int idPeminjaman, Alat alat, Integer jumlah) {
        this.idPeminjaman = idPeminjaman;
        this.alat = alat;
        this.jumlah = jumlah;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPeminjaman() {
        return idPeminjaman;
    }

    public void setIdPeminjaman(int idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    public Alat getAlat() {
        return alat;
    }

    public void setAlat(Alat alat) {
        this.alat = alat;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    private static int getLastId() {
        JsonReader reader = getReader(JSON_PATH);
        DetailPenjualan[] detailPenjualans = gson.fromJson(reader, DetailPenjualan[].class);
        if (detailPenjualans == null) {
            return 1;
        } else {
            return (detailPenjualans[detailPenjualans.length - 1].getId()) + 1;
        }

    }

    private static void writeToJson(ArrayList<DetailPeminjaman> detailPeminjamans) {
        String jsonString = gson.toJson(detailPeminjamans);

        FileWriter writer = getWriter(JSON_PATH);
        try {
            writer.write(jsonString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList getAll() {
        BufferedReader buffer;
        ArrayList<DetailPeminjaman> detailPeminjamans = null;
        try {
            buffer = new BufferedReader(new FileReader(JSON_PATH));
            if (buffer.readLine() == null) {
                return detailPeminjamans;
            } else {
                JsonReader reader = getReader(JSON_PATH);
                detailPeminjamans = gson.fromJson(reader, new TypeToken<ArrayList<DetailPeminjaman>>() {
                }.getType());

                return detailPeminjamans;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return detailPeminjamans;
    }

    public static Boolean store(ArrayList<Pair<Integer, Integer>> dataPeminjaman, Integer idPeminjaman) {

        ArrayList<DetailPeminjaman> detailPeminjamans = new ArrayList<DetailPeminjaman>();
        ArrayList<DetailPeminjaman> updateSetPeminjaman = new ArrayList<DetailPeminjaman>();

        for (int i = 0; i < dataPeminjaman.size(); i++) {
            Alat alat = null;
            try {
                alat = Alat.findId(dataPeminjaman.get(i).getKey());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            detailPeminjamans = DetailPeminjaman.getAll();
            DetailPeminjaman detailPeminjaman = new DetailPeminjaman(idPeminjaman, alat, dataPeminjaman.get(i).getValue());

            if (detailPeminjamans == null) {
                ArrayList<DetailPeminjaman> detailPeminjamans2 = new ArrayList<DetailPeminjaman>();
                detailPeminjamans2.add(detailPeminjaman);
                writeToJson(detailPeminjamans2);
            } else {
                detailPeminjamans.add(detailPeminjaman);
                writeToJson(detailPeminjamans);
            }
            updateSetPeminjaman.add(detailPeminjaman);
        }

        Peminjaman peminjaman = Peminjaman.findId(idPeminjaman);
        peminjaman.setDetailPeminjaman(updateSetPeminjaman);
        peminjaman.update(peminjaman);

        return true;
    }

    public static Boolean Update(ArrayList<DetailPeminjaman> detailPeminjamans, ArrayList<Pair<Integer, Integer>> dataPeminjaman, Integer idPeminjaman) throws IOException {

        for (int i = 0; i < dataPeminjaman.size(); i++) {
            Alat alat = Alat.findId(dataPeminjaman.get(i).getKey());

            detailPeminjamans.get(i).setJumlah(dataPeminjaman.get(i).getValue());
            detailPeminjamans.get(i).setAlat(alat);

        }

        ArrayList<DetailPeminjaman> detailPeminjamans1 = DetailPeminjaman.getAll();

        int index = 0;

        for (DetailPeminjaman dp1 : detailPeminjamans) {
            for (DetailPeminjaman dp2 : detailPeminjamans1) {
                if (dp2.getId() == dp1.getId()) {
                    detailPeminjamans1.set(index, dp1);
                    index = 0;
                    break;
                }
                index++;
            }
        }

        writeToJson(detailPeminjamans1);

        Peminjaman peminjaman = Peminjaman.findId(idPeminjaman);
        peminjaman.setDetailPeminjaman(detailPeminjamans);
        peminjaman.update(peminjaman);

        return true;
    }

    public static void delete(Integer idPeminjaman) {
        ArrayList<DetailPeminjaman> detailPeminjamans = null;
        detailPeminjamans = getAll();
        for (int i = detailPeminjamans.size() - 1; i >= 0; i--) {
            if (detailPeminjamans.get(i).getIdPeminjaman() == idPeminjaman) {
                detailPeminjamans.remove(i);
            }
        }

        writeToJson(detailPeminjamans);
    }

}
