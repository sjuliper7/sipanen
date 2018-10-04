package Model;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import javafx.util.Pair;
import java.io.*;
import java.util.ArrayList;

public class DetailPenjualan extends Model{
    
    private static final String JSON_PATH = "database\\detailPenjualans.json";
    
    private int id;
    private int idPenjualan;
    private Bahan bahan;
    private Integer jumlah;
    private Double subtotalHarga;
    
    public DetailPenjualan(int idPenjualan, Bahan bahan, Integer jumlah) {
        this.idPenjualan = idPenjualan;
        this.bahan = bahan;
        this.jumlah = jumlah;
        this.subtotalHarga = getCountSubTotalHarga(bahan, jumlah);
        this.id = getLastId();
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdPenjualan() {
        return idPenjualan;
    }
    
    public void setIdPenjualan(int idPenjualan) {
        this.idPenjualan = idPenjualan;
    }
    
    public Bahan getBahan() {
        return bahan;
    }
    
    public void setBahan(Bahan bahan) {
        this.bahan = bahan;
    }
    
    public Integer getJumlah() {
        return jumlah;
    }
    
    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }
    
    public Double getSubtotalHarga() {
        return subtotalHarga;
    }
    
    public void setSubtotalHarga(Double subtotalHarga) {
        this.subtotalHarga = subtotalHarga;
    }
    
    private static Double getCountSubTotalHarga(Bahan bahan, Integer qty) {
        return bahan.getHarga() * qty;
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
    
    private static void writeToJson(ArrayList<DetailPenjualan> detailPenjualans) {
        String jsonString = gson.toJson(detailPenjualans);
        
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
        ArrayList<DetailPenjualan> detailPenjualans = null;

        try {
            buffer = new BufferedReader(new FileReader(JSON_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (buffer.readLine() == null) {
                return detailPenjualans;
            } else {
                JsonReader reader = getReader(JSON_PATH);
                detailPenjualans = gson.fromJson(reader, new TypeToken<ArrayList<DetailPenjualan>>() {
                }.getType());

                return detailPenjualans;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public static Boolean store(ArrayList<Pair<Integer, Integer>> dataPenjualan, Integer idPenjualan) throws IOException {
        
        ArrayList<DetailPenjualan> detailPenjualans = new ArrayList<DetailPenjualan>();
        ArrayList<DetailPenjualan> updateSetPenjualan = new ArrayList<DetailPenjualan>();
        Double totalHarga = 0.0;
        
        for (int i = 0; i < dataPenjualan.size(); i++) {
            Bahan bahan = Bahan.findId(dataPenjualan.get(i).getKey());
            detailPenjualans = DetailPenjualan.getAll();
            DetailPenjualan detailPenjualan = new DetailPenjualan(idPenjualan, bahan, dataPenjualan.get(i).getValue());
            
            totalHarga += detailPenjualan.getSubtotalHarga();
            
            if (detailPenjualans == null) {
                ArrayList<DetailPenjualan> detailPenjualans2 = new ArrayList<DetailPenjualan>();
                detailPenjualans2.add(detailPenjualan);
                writeToJson(detailPenjualans2);
            } else {
                detailPenjualans.add(detailPenjualan);
                writeToJson(detailPenjualans);
            }
            updateSetPenjualan.add(detailPenjualan);
        }
        
        Penjualan penjualan = Penjualan.findId(idPenjualan);
        penjualan.setDetailPenjualans(updateSetPenjualan);
        penjualan.setTotalHarga(totalHarga);
        Penjualan.update(penjualan);
        
        return true;
    }
    
    public static Boolean Update(ArrayList<DetailPenjualan> detailPenjualans, ArrayList<Pair<Integer, Integer>> dataPenjualan, Integer idPenjualan) throws IOException {
        
        Double totalHarga = 0.0;
        
        for (int i = 0; i < dataPenjualan.size(); i++) {
            Bahan bahan = Bahan.findId(dataPenjualan.get(i).getKey());
            
            detailPenjualans.get(i).setBahan(bahan);
            detailPenjualans.get(i).setJumlah(dataPenjualan.get(i).getValue());
            
            detailPenjualans.get(i).setSubtotalHarga(getCountSubTotalHarga(bahan, dataPenjualan.get(i).getValue()));
            
            totalHarga += detailPenjualans.get(i).getSubtotalHarga();
        }
        
        ArrayList<DetailPenjualan> detailPenjualans2 = DetailPenjualan.getAll();
        
        int index = 0;
        
        for (DetailPenjualan dp1 : detailPenjualans) {
            for (DetailPenjualan dp2 : detailPenjualans2) {
                if (dp2.getId() == dp1.getId()) {
                    detailPenjualans2.set(index, dp1);
                    index = 0;
                    break;
                }
                index++;
            }
        }
        
        writeToJson(detailPenjualans2);
        
        Penjualan penjualan = Penjualan.findId(idPenjualan);
        penjualan.setDetailPenjualans(detailPenjualans);
        penjualan.setTotalHarga(totalHarga);
        
        Penjualan.update(penjualan);
        
        return true;
    }
    
    public static void delete(Integer idPenjualan) {
        ArrayList<DetailPenjualan> detailsPenjualans = getAll();
        
        for (int i = detailsPenjualans.size() - 1; i >= 0; i--) {
            if (detailsPenjualans.get(i).getIdPenjualan() == idPenjualan) {
                detailsPenjualans.remove(i);
            }
        }
        
        writeToJson(detailsPenjualans);
    }
    
}
