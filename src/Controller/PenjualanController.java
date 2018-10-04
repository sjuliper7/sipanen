package Controller;

import Model.Bahan;
import Model.DetailPenjualan;
import Model.Member;
import Model.Penjualan;
import View.BahanView;
import View.PenjualanView;
import javafx.util.Pair;
import java.io.IOException;
import java.util.ArrayList;

public class PenjualanController {

    public static void getMenu() {
        ArrayList<Penjualan> penjualans;
        try {
            penjualans = Penjualan.getAll();
            PenjualanView.penjualanShowAll(penjualans);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            PenjualanView.Menu();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void create() {
        ArrayList<Bahan> bahans;

        bahans = Bahan.getAll();
        BahanView.bahanShowAll(bahans);

        PenjualanView.FormTambahPenjualan();
    }

    public static void store(ArrayList<Pair<Integer, Integer>> dataPenjualan, Integer idMember) {
        Member member;
        try {
            member = Member.findId(idMember);
            ArrayList<DetailPenjualan> detailPenjualans = null;

            Penjualan penjualan = new Penjualan(member, detailPenjualans);

            if (Penjualan.store(penjualan)) {
                if (DetailPenjualan.store(dataPenjualan, penjualan.getId())) {
                    System.out.println("Transaksi Berhasil Dilakukan");
                    PenjualanController.getMenu();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static void getEditForm() {
        try {
            PenjualanView.formEditPenjualan();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void edit(Integer id) {
        Penjualan penjualan = Penjualan.findId(id);
        PenjualanView.formUpdatePenjualan(penjualan);
    }

    public static void update(ArrayList<DetailPenjualan> detailPenjualans, ArrayList<Pair<Integer, Integer>> dataPenjualan, Integer idMember, Integer idPenjualan) {
        Penjualan penjualan = Penjualan.findId(idPenjualan);
        Member member = Member.findId(idMember);
        penjualan.setMember(member);
        try {
            if (Penjualan.update(penjualan)) {
                if (DetailPenjualan.Update(detailPenjualans, dataPenjualan, idPenjualan)) {
                    System.out.println("Update Berhasil");
                    PenjualanController.getMenu();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void delete(Integer idPenjualan) {
        Penjualan penjualan = Penjualan.findId(idPenjualan);

        Penjualan.delete(idPenjualan);

        DetailPenjualan.delete(idPenjualan);

        PenjualanController.getMenu();
    }

    public static void getFormDelete() throws IOException {
        PenjualanView.formDelete();
        PenjualanController.getMenu();
    }

}
