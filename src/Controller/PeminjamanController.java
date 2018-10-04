package Controller;

import Model.Alat;
import Model.DetailPeminjaman;
import Model.Member;
import Model.Peminjaman;
import View.AlatView;
import View.PeminjamanView;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;

public class PeminjamanController {

    public static void getMenu() {
        ArrayList<Peminjaman> peminjamans;
        try {
            peminjamans = Peminjaman.getAll();
            PeminjamanView.peminjamanShowAll(peminjamans);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        PeminjamanView.Menu();
    }

    public static void create() {
        ArrayList<Alat> alats;

        alats = Alat.getAll();
        AlatView.alatShowAll(alats);

        PeminjamanView.formTambahPeminjaman();
    }

    public static void store(ArrayList<Pair<Integer, Integer>> dataPeminjaman, Integer idMember, Integer jumlahHari, String tanggalMulai) {
        Member member;
        member = Member.findId(idMember);
        ArrayList<DetailPeminjaman> detailPeminjamans = null;
        Peminjaman peminjaman = new Peminjaman(member, detailPeminjamans, tanggalMulai, jumlahHari);
        if (Peminjaman.store(peminjaman)) {
            if (DetailPeminjaman.store(dataPeminjaman, peminjaman.getId())) {
                System.out.println("Transaksi Berhasil Dilakukan");
                PeminjamanController.getMenu();
            }
        }

    }

    public static void getEditForm() {
        PeminjamanView.formEditPenjualan();
    }

    public static void edit(Integer id) {
        Peminjaman peminjaman = Peminjaman.findId(id);

        PeminjamanView.formUpdatePenjualan(peminjaman);
    }

    public static void update(ArrayList<DetailPeminjaman> detailPeminjamans, ArrayList<Pair<Integer, Integer>> dataPeminjaman, Integer idMember, Integer idPeminjaman, String tanggalMulai, Integer jumlahHari) {
        Peminjaman peminjaman = Peminjaman.findId(idPeminjaman);
        Member member = Member.findId(idMember);
        peminjaman.setMember(member);
        try {
            if (Peminjaman.update(peminjaman)) {
                if (DetailPeminjaman.Update(detailPeminjamans, dataPeminjaman, idPeminjaman)) {
                    System.out.println("Update Berhasil");
                    PeminjamanController.getMenu();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void delete(Integer idPeminjaman) {
        Peminjaman.delete(idPeminjaman);

        DetailPeminjaman.delete(idPeminjaman);

        PeminjamanController.getMenu();
    }

    public static void getFormDelete() {
        PeminjamanView.formDelete();
        PeminjamanController.getMenu();
    }
}
