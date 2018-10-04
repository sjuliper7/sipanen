package View;

import Controller.PanenController;
import Model.DetailPanen;
import Model.Panen;
import java.util.ArrayList;
import javafx.util.Pair;

public class PanenView extends View {

    public static void menu() {
        System.out.println("1. Tambah Data Panen");
        System.out.println("2. Ubah Data Panen");
        System.out.println("3. Hapus Data Panen");
        System.out.println("9. Kembali ke Menu Utama");
        System.out.print("Masukkan pilihan menu: ");
        Integer option = scanner.nextInt();

        if (option == 1) {
            PanenController.create();
        } else if (option == 2) {
            PanenController.getEditForm();
        } else if (option == 3) {
            PanenController.getFormDelete();
        }

    }

    public static void panenShowAll(ArrayList<Panen> panens) {
        System.out.println("=== Daftar Data PANEN ===");
        System.out.println("ID \tNama Member \t\tTanggal Lapor");
        if (panens == null) {
            System.out.println("Daftar Data Panen Kosong");
        } else {
            for (Panen panen : panens) {
                System.out.print(panen.getId() + "\t\t");
                System.out.print(panen.getMember().getNama() + "\t\t");
                System.out.println(panen.getTanggalLapor());
            }
        }
    }

    public static void formTambahPanen() {
        System.out.println("Contoh Fomat Tanggal : 2017/12/29");
        System.out.println("Jika Sekarang masukkan 'sekarang'");

        ArrayList<String> tanggalPanens = new ArrayList<String>();
        ArrayList<Pair<Integer, Integer>> dataPanen = new ArrayList<Pair<Integer, Integer>>();

        System.out.print("Masukkan ID Member: ");
        Integer idMember = scanner.nextInt();
        System.out.println("Masukkan ID Komoditas dan Jumlahnya");
        Integer id = 0, qty = 0;
        String tanggalPanen;
        while (true) {
            System.out.print("ID: ");
            id = scanner.nextInt();

            System.out.print("Jumlah: ");
            qty = scanner.nextInt();

            System.out.print("Tanggal Panen: ");
            tanggalPanen = scanner.next();

            if (id == 0 || qty == 0) {
                break;
            } else {
                dataPanen.add(new Pair<Integer, Integer>(id, qty));
                tanggalPanens.add(tanggalPanen);
            }
        }

        PanenController.store(dataPanen, idMember, tanggalPanens);
    }

    public static void formEditPenjualan() {
        System.out.print("Masukkan ID Data Panen yang ingin diubah: ");
        Integer id = scanner.nextInt();
        PanenController.edit(id);
    }

    public static void formUpdate(Panen panen) {
        System.out.println("============= Form Perubahan ============");
        System.out.println("Bubuhkan 0 jika tidak perlu melakukan perubahan");

        System.out.println("========Data=======");
        System.out.println("ID : " + panen.getId());
        System.out.println("Nama : " + panen.getMember().getNama());
        System.out.println("Tanggal Lapor : " + panen.getTanggalLapor());

        System.out.println("Hasil Panen");
        ArrayList<DetailPanen> detailPanens = panen.getDetailPanens();
        for (DetailPanen dp : detailPanens) {
            System.out.println("Nama Komoditas: " + dp.getKomoditas().getName());
            System.out.println("Jumlah : " + dp.getJumlah() + " " + dp.getKomoditas().getSatuan());
            System.out.println("Tanggal Panen : " + dp.getTanggalPanen());
        }

        ArrayList<String> tanggalPanens = new ArrayList<String>();
        ArrayList<Pair<Integer, Integer>> dataPanen = new ArrayList<Pair<Integer, Integer>>();

        System.out.print("Masukkan ID Member: ");
        Integer idMember = scanner.nextInt();
        System.out.println("Masukkan ID Komoditas dan Jumlahnya");
        Integer id = 0, qty = 0, index = 0;
        String tanggalPanen;
        while (index < detailPanens.size()) {
            System.out.print("ID: ");
            id = scanner.nextInt();

            System.out.print("Jumlah: ");
            qty = scanner.nextInt();

            System.out.print("Tanggal Panen: ");
            tanggalPanen = scanner.next();

            if (id == 0 || qty == 0) {
                break;
            } else {
                dataPanen.add(new Pair<Integer, Integer>(id, qty));
                tanggalPanens.add(tanggalPanen);
            }
            index++;
        }
        PanenController.update(panen.getId(), idMember, detailPanens, dataPanen, tanggalPanens);
    }

    public static void formDelete() {
        System.out.print("Masukkan ID Data Panen yang ingin dihapus: ");
        Integer option = scanner.nextInt();
        PanenController.delete(option);

    }
}
