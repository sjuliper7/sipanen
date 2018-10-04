package View;

import Controller.BahanController;
import Controller.HomeController;
import Model.Bahan;
import java.util.ArrayList;

public class BahanView extends View {

    public static void showMenu() {
        System.out.println("1. Tambah");
        System.out.println("2. Ubah");
        System.out.println("3. Hapus");
        System.out.println("9. Kembali ke Menu Utama");
        System.out.print("Masukkan pilihan menu: ");
        Integer option = scanner.nextInt();

        if (option == 1) {
            BahanController.create();
        } else if (option == 2) {
            BahanController.getEditForm();
        } else if (option == 3) {
            delete();
        } else if (option == 9) {
            HomeController.getHomeMenu();
        }

    }

    public static void showCreateForm() {
        System.out.println("===> Create From Bahan");
        System.out.print("Masukkan Nama: ");
        String buffer = scanner.nextLine();
        String nama = scanner.nextLine();

        System.out.print("Masukkan Jumlah: ");
        Integer jumlah = scanner.nextInt();

        System.out.print("Masukkan Harga: ");
        Double harga = scanner.nextDouble();

        System.out.print("Masukkan Deskprisi: ");
        buffer = scanner.nextLine();
        String deskripsi = scanner.nextLine();

        try {
            BahanController.store(nama, jumlah, deskripsi, harga);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void bahanShowAll(ArrayList<Bahan> bahans) {
        System.out.println("=== Daftar Semua Bahan ===");
        System.out.println("ID \tNama \t\tQty \t\tHarga \t\tDeskripsi");

        if (bahans == null) {
            System.out.println("Bahan Kosong");
        } else {
            for (Bahan bahan : bahans) {
                System.out.print(bahan.getId() + "\t");
                System.out.print(bahan.getNama() + "\t\t");
                System.out.print(bahan.getKuantitas() + "\t\t");
                System.out.print(bahan.getHarga() + "\t\t");
                System.out.println(bahan.getDeskripsi());
            }
        }
    }

    public static void editBahan() {
        System.out.print("Masukkan ID Bahan yang ingin di ubah: ");
        int id = scanner.nextInt();

        BahanController.Edit(id);
    }

    public static void delete() {
        System.out.print("Masukkan ID Bahan yang ingin di hapus: ");
        int id = scanner.nextInt();

        BahanController.delete(id);
    }

    public static void updateBahan(Bahan bahan) {
        System.out.println("Update Bahan: ");
        System.out.print(bahan.getId() + "\t");
        System.out.print(bahan.getNama() + "\t\t");
        System.out.print(bahan.getKuantitas() + "\t\t");
        System.out.println(bahan.getDeskripsi());

        System.out.println("Jika tidak ingin mengubah isi dengan -");
        System.out.print("Masukkan Nama: ");
        String buffer = scanner.nextLine();
        String nama = scanner.nextLine();

        System.out.print("Masukkan Jumlah: ");
        String qty = scanner.next();

        System.out.print("Masukkan Harga: ");
        Double harga = scanner.nextDouble();

        System.out.print("Masukkan Deskprisi: ");
        buffer = scanner.nextLine();
        String deskripsi = scanner.nextLine();

        if (!nama.equalsIgnoreCase("-")) {
            bahan.setNama(nama);
        }
        if (!qty.equalsIgnoreCase("-")) {
            Integer qty2 = Integer.parseInt(qty);
            bahan.setKuantitas(qty2);
        }
        if (!deskripsi.equalsIgnoreCase("-")) {
            bahan.setDeskripsi(deskripsi);
        }

        BahanController.update(bahan);
    }

}
