package Model;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import static Model.Model.gson;

public class Penjualan {

    private static final String JSON_PATH = "database\\penjualans.json";
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private int id;
    private Member member;
    private ArrayList<DetailPenjualan> detailPenjualans;
    private Double totalHarga;
    private String tanggalPenjualan;

    public Penjualan(Member member, ArrayList<DetailPenjualan> detailPenjualans) {
        this.member = member;
        this.detailPenjualans = detailPenjualans;
        this.totalHarga = 0.0;
        Date date = new Date();
        this.tanggalPenjualan = sdf.format(date);
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

    public ArrayList<DetailPenjualan> getDetailPenjualans() {
        return detailPenjualans;
    }

    public void setDetailPenjualans(ArrayList<DetailPenjualan> detailPenjualans) {
        this.detailPenjualans = detailPenjualans;
    }

    public Double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(Double totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getTanggalPenjualan() {
        return tanggalPenjualan;
    }

    private static int getLastId() {
        JsonReader reader = getReader();
        Penjualan[] penjualans = gson.fromJson(reader, Penjualan[].class);
        if (penjualans == null) {
            return 1;
        } else {
            return (penjualans[penjualans.length - 1].getId()) + 1;
        }

    }

    private static JsonReader getReader() {
        try {
            JsonReader reader = new JsonReader(new FileReader(JSON_PATH));
            return reader;
        } catch (Exception e) {
            System.err.println(e);
        }

        return null;
    }

    private static FileWriter getWriter() {

        try {
            FileWriter writer = new FileWriter(JSON_PATH);
            return writer;
        } catch (IOException ex) {
            System.err.println(ex);
        }

        return null;
    }

    private static void writeToJson(ArrayList<Penjualan> penjualans) {
        String jsonString = gson.toJson(penjualans);

        FileWriter writer = getWriter();
        try {
            writer.write(jsonString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Boolean store(Penjualan penjualan) {
        JsonReader reader = getReader();
        ArrayList<Penjualan> penjualans = gson.fromJson(reader, new TypeToken<ArrayList<Penjualan>>() {
        }.getType());
        penjualan.setDetailPenjualans(null);
        if (penjualans == null) {
            ArrayList<Penjualan> penjualans_2 = new ArrayList<Penjualan>();
            penjualans_2.add(penjualan);
            writeToJson(penjualans_2);
            return true;
        } else {
            penjualans.add(penjualan);
            writeToJson(penjualans);
            return true;
        }

    }

    public static Boolean update(Penjualan penjualan) {
        ArrayList<Penjualan> penjualans = getAll();

        int index = 0;

        for (Penjualan pjln : penjualans) {
            if (pjln.getId() == penjualan.getId()) {
                penjualans.set(index, penjualan);
                break;
            }
            index++;
        }

        writeToJson(penjualans);

        return true;
    }

    public static ArrayList getAll() {
        BufferedReader buffer = null;
        ArrayList<Penjualan> penjualans = null;

        try {
            buffer = new BufferedReader(new FileReader(JSON_PATH));
        } catch (Exception e) {
            System.err.println(e);
        }

        try {
            if (buffer.readLine() == null) {
                return penjualans;
            } else {
                JsonReader reader = getReader();
                penjualans = gson.fromJson(reader, new TypeToken<ArrayList<Penjualan>>() {
                }.getType());

                return penjualans;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Penjualan findId(int id) {
        ArrayList<Penjualan> penjualans = getAll();

        for (Penjualan penjualan : penjualans) {
            if (penjualan.getId() == id) {
                return penjualan;
            }
        }

        return null;
    }

    public static void delete(Integer id) {
        ArrayList<Penjualan> penjualans = getAll();
        int index = 0;

        for (Penjualan penjualan : penjualans) {
            if (penjualan.getId() == id) {
                penjualans.remove(index);
                break;
            }
            index++;
        }
        writeToJson(penjualans);
    }

    public static ArrayList<Penjualan> getPerbulan(int year, int month) {
        ArrayList<Penjualan> penjualans = getAll();
        ArrayList<Penjualan> selectedPenjualans = new ArrayList<Penjualan>();

        for (Penjualan penjualan : penjualans) {
            String transactionDateOnString = penjualan.getTanggalPenjualan();
            Date transcationDate = parseDate(transactionDateOnString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(transcationDate);

            int transactionYear = calendar.get(Calendar.YEAR);
            int transactionMonth = calendar.get(Calendar.MONTH) + 1;

            if (transactionYear == year && transactionMonth == month) {
                selectedPenjualans.add(penjualan);
            }
        }

        return selectedPenjualans;
    }

    private static Date parseDate(String transactionDateOnString) {
        String format = "yyyy/MM/dd HH:mm:ss";
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(transactionDateOnString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static ArrayList<Penjualan> findByBahanId(int id) {
        ArrayList<Penjualan> penjualans = getAll();
        ArrayList<Penjualan> selectedPenjualans = new ArrayList<Penjualan>();

        for (Penjualan penjualan : penjualans) {
            ArrayList<DetailPenjualan> detailPenjualans = penjualan.getDetailPenjualans();

            for (DetailPenjualan dp : detailPenjualans) {
                Bahan bahan = dp.getBahan();

                if (bahan.getId() == id) {
                    selectedPenjualans.add(penjualan);
                }
            }
        }

        return selectedPenjualans;
    }

    public static ArrayList<Penjualan> findByMemberId(int id) {
        ArrayList<Penjualan> penjualans = getAll();
        ArrayList<Penjualan> selectedPenjualans = new ArrayList<Penjualan>();

        for (Penjualan penjualan : penjualans) {
            Member member = penjualan.getMember();

            if (member.getId() == id) {
                selectedPenjualans.add(penjualan);
            }
        }

        return selectedPenjualans;
    }
}
