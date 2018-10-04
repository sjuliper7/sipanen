package View;

import Controller.AlatController;
import Controller.HomeController;
import Model.Alat;
import java.util.ArrayList;

public class AlatView extends View {

    public static void showMenu() {
        System.out.println("1. Tambah");
        System.out.println("2. Ubah");
        System.out.println("3. Hapus");
        System.out.println("9. Kembali ke Menu Utama");
        System.out.print("Masukkan pilihan menu: ");
        Integer option = scanner.nextInt();

        if (option == 1) {
            AlatController.create();
        } else if (option == 2) {
            AlatController.getEditForm();
        } else if (option == 3) {
            delete();
        } else if (option == 9) {
            HomeController.getHomeMenu();
        }

    }

    public static void showCreateForm() {
        System.out.println("===> Create From Alat");
        String buff = scanner.nextLine();
        System.out.print("Masukkan Nama: ");
        String nama = scanner.nextLine();

        System.out.print("Masukkan Jumlah: ");
        Integer jumlah = scanner.nextInt();

        buff = scanner.nextLine();

        System.out.print("Masukkan Deskprisi: ");
        String deskripsi = scanner.nextLine();

        AlatController.store(nama, jumlah, deskripsi);
    }

    public static void alatShowAll(ArrayList<Alat> alats) {
        System.out.println("=== Daftar Semua Alat ===");
        System.out.println("ID \tNama \t\tQty \t\tDeskripsi");
        if (alats == null) {
            System.out.println("Daftar Alat Kosong");
        } else {
            for (Alat alat : alats) {
                System.out.print(alat.getId() + "\t");
                System.out.print(alat.getNama() + "\t\t");
                System.out.print(alat.getKuantitas() + "\t\t");
                System.out.println(alat.getDeskripsi());
            }
        }

    }

    public static void editAlat() {
        System.out.print("Masukkan ID Alat yang ingin diubah: ");
        Integer option = scanner.nextInt();

        AlatController.edit(option);
    }

    public static void delete() {
        System.out.print("Masukkan ID Alat yang ingin dihapus: ");
        Integer option = scanner.nextInt();

        AlatController.delete(option);
    }

    public static void updateAlat(Alat alat) {
        System.out.println("Update Alat:");
        System.out.print(alat.getId() + "\t");
        System.out.print(alat.getNama() + "\t\t");
        System.out.print(alat.getKuantitas() + "\t\t");
        System.out.println(alat.getDeskripsi());

        System.out.println("Jika tidak ingin mengubah isi dengan -");
        System.out.print("Nama: ");
        String nama = scanner.next();
        System.out.print("Kuantitas: ");
        String qty = scanner.next();
        System.out.print("Deskripsi: ");
        String deskripsi = scanner.next();

        if (!nama.equalsIgnoreCase("-")) {
            alat.setNama(nama);
        }
        if (!qty.equalsIgnoreCase("-")) {
            Integer qty2 = Integer.parseInt(qty);
            alat.setKuantitas(qty2);
        }
        if (!deskripsi.equalsIgnoreCase("-")) {
            alat.setDeskripsi(deskripsi);
        }

        AlatController.update(alat);
    }

}
