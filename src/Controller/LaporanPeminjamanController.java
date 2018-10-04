package Controller;

import Model.Alat;
import Model.Member;
import Model.Peminjaman;
import View.LaporanPeminjamanView;
import View.LaporanPenjualanView;

import java.util.ArrayList;

public class LaporanPeminjamanController {
    public static void getMenu(){
        LaporanPenjualanView.showMenu();
    }

    public static void getFormPerbulan(){
        LaporanPeminjamanView.showPerbulanForm();
    }

    public static void getLaporanPerbulan(int year, int month){
        ArrayList<Peminjaman> peminjamans = Peminjaman.getPerbulan(year, month);
        LaporanPeminjamanView.showLaporan(peminjamans);
    }

    public static void getFormPerbahan(){
        ArrayList<Alat> alats = Alat.getAll();
        LaporanPeminjamanView.showPeralatForm(alats);
    }

    public static void getLaporanPerbahan(int id){
        ArrayList<Peminjaman> peminjamans = Peminjaman.findByBahanId(id);

        LaporanPeminjamanView.showLaporan(peminjamans);
    }

    public static void getLaporanPerorang(int id){
        ArrayList<Peminjaman> peminjamans = Peminjaman.findByMemberId(id);

        LaporanPeminjamanView.showLaporan(peminjamans);
    }

    public static void getFormPerorang(){
        ArrayList<Member> members = Member.getAll();
        LaporanPeminjamanView.showFormPerorang(members);
    }

}
