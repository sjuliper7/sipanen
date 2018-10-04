package Model;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Peminjaman extends Model {

    private static final String JSON_PATH = "database\\peminjamans.json";
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    private static LocalDate localDate = null;
    private static Date date = null;

    private int id;
    private Member member;
    private ArrayList<DetailPeminjaman> detailPeminjaman;
    private String tanggalPeminjaman;
    private Integer jumlahHari;
    private String tanggalSelesai;

    public Peminjaman(Member member, ArrayList<DetailPeminjaman> detailPeminjaman, String tangggalMulai, Integer jumlahHari) {
        this.member = member;
        this.detailPeminjaman = detailPeminjaman;
        this.jumlahHari = jumlahHari;
        if (tangggalMulai.equalsIgnoreCase("Sekarang") || tangggalMulai.equalsIgnoreCase("sekarang")) {
            date = new Date();
        } else {
            date = new Date(tangggalMulai);
        }
        this.tanggalPeminjaman = sdf.format(date);
        localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localDate = localDate.plusDays(jumlahHari);
        this.tanggalSelesai = localDate.toString();
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

    public ArrayList<DetailPeminjaman> getDetailPeminjaman() {
        return detailPeminjaman;
    }

    public void setDetailPeminjaman(ArrayList<DetailPeminjaman> detailPeminjaman) {
        this.detailPeminjaman = detailPeminjaman;
    }

    public String getTanggalPeminjaman() {
        return tanggalPeminjaman;
    }

    public void setTanggalPeminjaman(String tanggalPenjualan) {
        this.tanggalPeminjaman = tanggalPenjualan;
    }

    public Integer getJumlahHari() {
        return jumlahHari;
    }

    public void setJumlahHari(Integer jumlahHari) {
        this.jumlahHari = jumlahHari;
    }

    public String getTanggalSelesai() {
        return tanggalSelesai;
    }

    public void setTanggalSelesai(String tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
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

    private static void writeToJson(ArrayList<Peminjaman> peminjamans) {
        String jsonString = gson.toJson(peminjamans);

        FileWriter writer = getWriter(JSON_PATH);
        try {
            writer.write(jsonString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Boolean store(Peminjaman peminjaman) {
        JsonReader reader = getReader(JSON_PATH);
        ArrayList<Peminjaman> peminjamans = gson.fromJson(reader, new TypeToken<ArrayList<Peminjaman>>() {
        }.getType());
        peminjaman.setDetailPeminjaman(null);
        if (peminjamans == null) {
            ArrayList<Peminjaman> peminjamans1 = new ArrayList<Peminjaman>();
            peminjamans1.add(peminjaman);
            writeToJson(peminjamans1);
            return true;
        } else {
            peminjamans.add(peminjaman);
            writeToJson(peminjamans);
            return true;
        }

    }

    public static Boolean update(Peminjaman peminjamanUpdate) {
        ArrayList<Peminjaman> peminjamans = getAll();

        int index = 0;

        for (Peminjaman peminjaman : peminjamans) {
            if (peminjaman.getId() == peminjamanUpdate.getId()) {
                peminjamans.set(index, peminjamanUpdate);
                break;
            }
            index++;
        }

        writeToJson(peminjamans);

        return true;
    }

    public static ArrayList getAll() {
        BufferedReader buffer = null;
        ArrayList<Peminjaman> peminjamans = null;

        try {
            buffer = new BufferedReader(new FileReader(JSON_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (buffer.readLine() == null) {
                return peminjamans;
            } else {
                JsonReader reader = getReader(JSON_PATH);
                peminjamans = gson.fromJson(reader, new TypeToken<ArrayList<Peminjaman>>() {
                }.getType());

                return peminjamans;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Peminjaman findId(int id) {
        try {
            ArrayList<Peminjaman> peminjamans = getAll();
            for (Peminjaman peminjaman : peminjamans) {
                if (peminjaman.getId() == id) {
                    return peminjaman;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static void delete(Integer id) {
        ArrayList<Peminjaman> peminjamans = getAll();
        int index = 0;

        for (Peminjaman peminjaman : peminjamans) {
            if (peminjaman.getId() == id) {
                peminjamans.remove(index);
                break;
            }
            index++;
        }
        writeToJson(peminjamans);
    }

    public static ArrayList<Peminjaman> getPerbulan(int year, int month) {
        ArrayList<Peminjaman> peminjamans = getAll();
        ArrayList<Peminjaman> selectedPeminjamans = new ArrayList<>();

        for (Peminjaman peminjaman : peminjamans) {
            String transactionDateOnString = peminjaman.getTanggalPeminjaman();
            Date transcationDate = parseDate(transactionDateOnString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(transcationDate);

            int transactionYear = calendar.get(Calendar.YEAR);
            int transactionMonth = calendar.get(Calendar.MONTH) + 1;

            if (transactionYear == year && transactionMonth == month) {
                selectedPeminjamans.add(peminjaman);
            }
        }

        return selectedPeminjamans;
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

    public static ArrayList<Peminjaman> findByBahanId(int id) {
        ArrayList<Peminjaman> peminjamans = getAll();
        ArrayList<Peminjaman> selectedPeminjamans = new ArrayList<Peminjaman>();

        for (Peminjaman penjualan : peminjamans) {
            ArrayList<DetailPeminjaman> detailPeminjamans = penjualan.getDetailPeminjaman();

            for (DetailPeminjaman dp : detailPeminjamans) {
                Alat alat = dp.getAlat();

                if (alat.getId() == id) {
                    selectedPeminjamans.add(penjualan);
                }
            }
        }

        return selectedPeminjamans;
    }

    public static ArrayList<Peminjaman> findByMemberId(int id) {
        ArrayList<Peminjaman> peminjamans = getAll();
        ArrayList<Peminjaman> selectedPeminjamans = new ArrayList<Peminjaman>();

        for (Peminjaman peminjaman : peminjamans) {
            Member member = peminjaman.getMember();

            if (member.getId() == id) {
                selectedPeminjamans.add(peminjaman);
            }
        }

        return selectedPeminjamans;
    }
}
