package Model;

import static Model.Model.getReader;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.util.Pair;

public class DetailPanen extends Model {

    private static final String JSON_PATH = "database\\detailPanens.json";

    private int id;
    private Integer idPanen;
    private Komoditas komoditas;
    private Integer jumlah;
    private String tanggalPanen;

    public DetailPanen(Integer idPanen, Komoditas komoditas, Integer jumlah, String tanggalPanen) {
        this.idPanen = idPanen;
        this.komoditas = komoditas;
        this.jumlah = jumlah;
        this.tanggalPanen = tanggalPanen;
        this.id = getLastId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdPanen() {
        return idPanen;
    }

    public void setIdPemilikPanen(Integer idPanen) {
        this.idPanen = idPanen;
    }

    public Komoditas getKomoditas() {
        return komoditas;
    }

    public void setKomoditas(Komoditas komoditas) {
        this.komoditas = komoditas;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public String getTanggalPanen() {
        return tanggalPanen;
    }

    public void setTanggalPanen(String tanggalPanen) {
        this.tanggalPanen = tanggalPanen;
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

    private static void writeToJson(ArrayList<DetailPanen> detailPanens) {
        String jsonString = gson.toJson(detailPanens);

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
        ArrayList<DetailPanen> detailPanens = null;
        try {
            buffer = new BufferedReader(new FileReader(JSON_PATH));
            if (buffer.readLine() == null) {
                return detailPanens;
            } else {
                JsonReader reader = getReader(JSON_PATH);
                detailPanens = gson.fromJson(reader, new TypeToken<ArrayList<DetailPanen>>() {
                }.getType());

                return detailPanens;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return detailPanens;
    }

    public static void store(ArrayList<Pair<Integer, Integer>> dataPanen, ArrayList<String> tanggalPanens, Integer idPanen) {
        ArrayList<DetailPanen> detailPanens = new ArrayList<DetailPanen>();
        ArrayList<DetailPanen> updateSetPanen = new ArrayList<DetailPanen>();

        for (int i = 0; i < dataPanen.size(); i++) {
            Komoditas komoditas = Komoditas.getOne(dataPanen.get(i).getKey());
            detailPanens = DetailPanen.getAll();
            DetailPanen detailPanen = new DetailPanen(idPanen, komoditas, dataPanen.get(i).getValue(), tanggalPanens.get(i));

            if (detailPanens == null) {
                ArrayList<DetailPanen> detailPanens2 = new ArrayList<DetailPanen>();
                detailPanens2.add(detailPanen);
                writeToJson(detailPanens2);
            } else {
                detailPanens.add(detailPanen);
                writeToJson(detailPanens);
            }
            updateSetPanen.add(detailPanen);
        }
        Panen panen = Panen.find(idPanen);
        panen.setDetailPanens(updateSetPanen);
        panen.update(panen);
    }

    public static void Update(ArrayList<DetailPanen> detailPanens, ArrayList<Pair<Integer, Integer>> dataPanen, ArrayList<String> tanggalPanens, Integer idPanen) {

        for (int i = 0; i < dataPanen.size(); i++) {
            Komoditas komoditas = Komoditas.getOne(dataPanen.get(i).getKey());
            detailPanens.get(i).setKomoditas(komoditas);
            detailPanens.get(i).setJumlah(dataPanen.get(i).getValue());
            detailPanens.get(i).setTanggalPanen(tanggalPanens.get(i));
        }

        ArrayList<DetailPanen> detailPanens1 = DetailPanen.getAll();

        int index = 0;

        for (DetailPanen dp1 : detailPanens) {
            for (DetailPanen dp2 : detailPanens1) {
                if (dp2.getId() == dp1.getId()) {
                    detailPanens1.set(index, dp1);
                    index = 0;
                    break;
                }
                index++;
            }
        }

        writeToJson(detailPanens1);
        Panen panen = Panen.find(idPanen);
        panen.setDetailPanens(detailPanens);
        Panen.update(panen);

    }

    public static void delete(Integer idPanen) {
        ArrayList<DetailPanen> detailPanens = null;
        detailPanens = getAll();
        for (int i = detailPanens.size() - 1; i >= 0; i--) {
            if (detailPanens.get(i).getIdPanen() == idPanen) {
                detailPanens.remove(i);
            }
        }

        writeToJson(detailPanens);
    }

}
