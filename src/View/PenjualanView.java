package View;

import Controller.HomeController;
import Controller.LaporanPenjualanController;
import Controller.PenjualanController;
import Model.DetailPenjualan;
import Model.Penjualan;
import javafx.util.Pair;
import java.io.IOException;
import java.util.ArrayList;

public class PenjualanView extends View {

    public static void Menu() throws IOException {
        System.out.println("1. Tambah Penjualan");
        System.out.println("2. Ubah Penjualan");
        System.out.println("3. Hapus Penjualan");
        System.out.println("4. Laporan Penjualan");
        System.out.println("9. Kembali ke Menu Utama");
        System.out.print("Masukkan pilihan menu: ");
        Integer option = scanner.nextInt();

        if (option == 1) {
            PenjualanController.create();
        } else if (option == 2) {
            PenjualanController.getEditForm();
        } else if (option == 3) {
            PenjualanController.getFormDelete();
        } else if(option == 4){
            LaporanPenjualanController.getMenu();
        } else if (option == 9) {
            HomeController.getHomeMenu();
        }
    }

    public static void penjualanShowAll(ArrayList<Penjualan> penjualans) {
        System.out.println("=== Daftar Semua Penjualan ===");
        System.out.println("ID \tNama Pembeli \t\tTotal Belanja\t\t Tanggal");
        if (penjualans == null) {
            System.out.println("Daftar Penjualan Kosong");
        } else {
            for (Penjualan penjualan : penjualans) {
                System.out.print(penjualan.getId() + "\t\t");
                System.out.print(penjualan.getMember().getNama() + "\t\t");
                System.out.print(penjualan.getTotalHarga()+"\t\t");
                System.out.println(penjualan.getTanggalPenjualan());
            }
        }
    }

    public static void FormTambahPenjualan() {
        System.out.print("Masukkan ID Member: ");
        Integer idMember = scanner.nextInt();

        System.out.println("Masukkan ID Bahan dan Jumlahnya");
        ArrayList<Pair<Integer, Integer>> dataPenjualan = new ArrayList<Pair<Integer, Integer>>();

        Integer id = 0, qty = 0;
        while (true) {
            System.out.print("ID: ");
            id = scanner.nextInt();
            System.out.print("Jumlah: ");
            qty = scanner.nextInt();
            if (id == 0 || qty == 0) {
                break;
            } else {
                dataPenjualan.add(new Pair<Integer, Integer>(id, qty));
            }
        }

        PenjualanController.store(dataPenjualan, idMember);
    }

    public static void formUpdatePenjualan(Penjualan penjualan) {
        System.out.println("============= Form Perubahan ============");
        System.out.println("Bubuhkan 0 jika tidak perlu melakukan perubahan");

        System.out.println("========Data=======");
        System.out.println("ID : " + penjualan.getId());
        System.out.println("Nama : " + penjualan.getMember().getNama());
        System.out.println("Total Harga : " + penjualan.getTotalHarga());

        System.out.println("Daftar yang di beli");
        ArrayList<DetailPenjualan> detailPenjualans = penjualan.getDetailPenjualans();
        for (DetailPenjualan dp : detailPenjualans) {
            System.out.println("\tBahan : " + dp.getBahan().getNama());
            System.out.println("\tJumlah : " + dp.getJumlah());
            System.out.println("\tSub Total Harga : " + dp.getSubtotalHarga());
        }

        System.out.print("Masukkan ID Member: ");
        String idMember = scanner.next();

        System.out.println("Masukkan ID Bahan dana Jumlahnya");
        ArrayList<Pair<Integer, Integer>> dataPenjualans = new ArrayList<Pair<Integer, Integer>>();

        Integer id = 0, qty = 0;
        int index = 0;
        while (index < detailPenjualans.size()) {
            System.out.print("ID: ");
            id = scanner.nextInt();
            System.out.print("Jumlah: ");
            qty = scanner.nextInt();

            dataPenjualans.add(new Pair<Integer, Integer>(id, qty));
            index++;

        }

        if ((!idMember.equalsIgnoreCase("-")) || (!idMember.equalsIgnoreCase("0"))) {
            PenjualanController.update(detailPenjualans, dataPenjualans, penjualan.getMember().getId(), penjualan.getId());
        } else {
            Integer idMember2 = Integer.parseInt(idMember);
            PenjualanController.update(detailPenjualans, dataPenjualans, idMember2, penjualan.getId());
        }

    }

    public static void formEditPenjualan(){
        System.out.print("Masukkan ID Penjualan yang ingin di ubah: ");
        Integer id = scanner.nextInt();
        PenjualanController.edit(id);

    }

    public static void formDelete(){
        System.out.print("Masukkan ID Penjualan yang ingin di hapus: ");
        Integer id = scanner.nextInt();
        PenjualanController.delete(id);

    }

}
