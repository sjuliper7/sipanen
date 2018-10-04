package View;

import Controller.HomeController;
import Controller.LaporanPeminjamanController;
import Controller.PeminjamanController;
import Model.DetailPeminjaman;
import Model.Peminjaman;
import javafx.util.Pair;
import java.util.ArrayList;

public class PeminjamanView extends View {

    public static void Menu() {
        System.out.println("1. Tambah Peminjaman");
        System.out.println("2. Ubah Peminjaman");
        System.out.println("3. Hapus Peminjaman");
        System.out.println("4. Laporan Peminjaman");
        System.out.println("9. Kembali ke Menu Utama");
        System.out.print("Masukkan pilihan menu: ");
        Integer option = scanner.nextInt();

        if (option == 1) {
            PeminjamanController.create();
        } else if (option == 2) {
            PeminjamanController.getEditForm();
        } else if (option == 3) {
            PeminjamanController.getFormDelete();
        } else if (option == 4) {
            LaporanPeminjamanController.getMenu();
        } else if (option == 9) {
            HomeController.getHomeMenu();
        }
    }

    public static void peminjamanShowAll(ArrayList<Peminjaman> peminjamans) {
        System.out.println("=== Daftar Semua Peminjaman ===");
        System.out.println("ID \tNama Peminjam\t\t Tanggal Peminjaman\t\t Tanggal Selesai");
        if (peminjamans == null) {
            System.out.println("Daftar Peminjaman Kosong");
        } else {
            for (Peminjaman peminjaman : peminjamans) {
                System.out.print(peminjaman.getId() + "\t\t");
                System.out.print(peminjaman.getMember().getNama() + "\t\t");
                System.out.print(peminjaman.getTanggalPeminjaman() + "\t\t");
                System.out.println(peminjaman.getTanggalSelesai());
            }
        }
    }

    public static void formTambahPeminjaman() {
        System.out.print("Masukkan ID Member: ");
        Integer idMember = scanner.nextInt();

        System.out.println("Contoh Fomat Tanggal : 2017/12/29");
        System.out.println("Jika Sekarang masukkan 'sekarang'");
        String buff = scanner.nextLine();

        System.out.print("Masukkan tanggal: ");
        String tanggalMulai = scanner.nextLine();

        System.out.print("Jumlah hari peminjaman: ");
        Integer jumlahHari = scanner.nextInt();
        System.out.println("Masukkan ID Alat dan Jumlahnya");

        ArrayList<Pair<Integer, Integer>> dataPeminjaman = new ArrayList<Pair<Integer, Integer>>();

        Integer id = 0, qty = 0;
        while (true) {
            System.out.print("ID: ");
            id = scanner.nextInt();
            System.out.print("Jumlah: ");
            qty = scanner.nextInt();
            if (id == 0 || qty == 0) {
                break;
            } else {
                dataPeminjaman.add(new Pair<Integer, Integer>(id, qty));
            }
        }

        PeminjamanController.store(dataPeminjaman, idMember, jumlahHari, tanggalMulai);
    }

    public static void formUpdatePenjualan(Peminjaman peminjaman) {
        System.out.println("============= Form Perubahan ============");
        System.out.println("Bubuhkan 0 jika tidak perlu melakukan perubahan");

        System.out.println("========Data=======");
        System.out.println("ID : " + peminjaman.getId());
        System.out.println("Nama : " + peminjaman.getMember().getNama());
        System.out.println("Tanggal Mulai : " + peminjaman.getTanggalPeminjaman());

        System.out.println("Daftar alat yang dipinjam");
        ArrayList<DetailPeminjaman> detailPeminjamans = peminjaman.getDetailPeminjaman();
        System.out.println(detailPeminjamans.size());
        for (DetailPeminjaman dp : detailPeminjamans) {
            System.out.println("Alat: " + dp.getAlat().getNama());
            System.out.println("Jumlah: " + dp.getJumlah());
        }

        System.out.print("Masukkan ID Member: ");
        String idMember = scanner.next();

        System.out.println("Contoh Fomat Tanggal : 2017/12/29");
        System.out.println("Jika Sekarang masukkan 'sekarang'");
        String buff = scanner.nextLine();

        System.out.print("Masukkan tanggal: ");
        String tanggalMulai = scanner.nextLine();

        System.out.print("Jumlah hari peminjaman: ");
        Integer jumlahHari = scanner.nextInt();

        System.out.println("Masukkan ID Alat dan Jumlahnya");
        ArrayList<Pair<Integer, Integer>> dataPeminjamans = new ArrayList<Pair<Integer, Integer>>();

        Integer id = 0, qty = 0;
        int index = 0;
        while (index < detailPeminjamans.size()) {
            System.out.print("ID: ");
            id = scanner.nextInt();
            System.out.print("Jumlah: ");
            qty = scanner.nextInt();

            dataPeminjamans.add(new Pair<Integer, Integer>(id, qty));
            index++;

        }

        if ((!idMember.equalsIgnoreCase("-")) || (!idMember.equalsIgnoreCase("0"))) {
            PeminjamanController.update(detailPeminjamans, dataPeminjamans, peminjaman.getMember().getId(), peminjaman.getId(), tanggalMulai, jumlahHari);
        } else {
            Integer idMember2 = Integer.parseInt(idMember);
            PeminjamanController.update(detailPeminjamans, dataPeminjamans, idMember2, peminjaman.getId(), tanggalMulai, jumlahHari);
        }

    }

    public static void formEditPenjualan() {
        System.out.print("Masukkan ID Peminjaman yang ingin diubah: ");
        Integer id = scanner.nextInt();
        PeminjamanController.edit(id);

    }

    public static void formDelete() {
        System.out.print("Masukkan ID Peminjaman yang ingin dihapus: ");
        Integer id = scanner.nextInt();
        PeminjamanController.delete(id);

    }
}
