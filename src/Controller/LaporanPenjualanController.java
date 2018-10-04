package Controller;

import Model.Bahan;
import Model.Member;
import Model.Penjualan;
import View.LaporanPenjualanView;

import java.util.ArrayList;

public class LaporanPenjualanController {

    public static void getMenu(){
        LaporanPenjualanView.showMenu();
    }

    public static void getFormPerbulan(){
        LaporanPenjualanView.showPerbulanForm();
    }

    public static void getLaporanPerbulan(int year, int month){
        ArrayList<Penjualan> penjualans = Penjualan.getPerbulan(year, month);
        LaporanPenjualanView.showLaporan(penjualans);
    }

    public static void getFormPerbahan(){
        ArrayList<Bahan> bahans = Bahan.getAll();
        LaporanPenjualanView.showPerbahanForm(bahans);
    }

    public static void getLaporanPerbahan(int id){
        ArrayList<Penjualan> penjualans = Penjualan.findByBahanId(id);

        LaporanPenjualanView.showLaporan(penjualans);
    }

    public static void getLaporanPerorang(int id){
        ArrayList<Penjualan> penjualans = Penjualan.findByMemberId(id);

        LaporanPenjualanView.showLaporan(penjualans);
    }

    public static void getFormPerorang(){
        ArrayList<Member> members = Member.getAll();
        LaporanPenjualanView.showFormPerorang(members);
    }
}
