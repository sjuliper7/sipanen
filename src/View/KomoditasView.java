package View;

import Controller.HomeController;
import Controller.KomoditasController;
import Model.Komoditas;

import java.util.ArrayList;

public class KomoditasView extends View {

    public static void showSubMenu() {
        System.out.println("1. Tambah");
        System.out.println("2. Ubah");
        System.out.println("3. Hapus");
        System.out.println("4. Lihat Semua");
        System.out.println("9. Kembali ke Menu Utama");
        System.out.print("Masukkan pilihan menu: ");
        option = scanner.nextInt();

        if (option == 1) {
            KomoditasController.getCreateForm();
        } else if (option == 2) {
            KomoditasController.startEditProceess();
        } else if (option == 3) {
            KomoditasController.startDeleteProceess();
        } else if (option == 4) {
            KomoditasController.getAllData();
            showSubMenu();
        } else if (option == 9) {
            HomeController.getHomeMenu();
        }
    }

    public static void showCreateForm() {
        System.out.println("===> Create From");
        System.out.print("Masukkan nama komoditas: ");
        String name = scanner.next();
        System.out.print("Masukkan satuan komoditas: ");
        String satuan = scanner.next();

        KomoditasController.store(name, satuan);
    }

    public static void showAllData(ArrayList<Komoditas> komoditas) {
        System.out.println("=== Daftar Semua Komoditas ===");
        System.out.println("ID.\tNama");

        for (Komoditas k : komoditas) {
            System.out.print(k.getId() + ".\t");
            System.out.println(k.getName());
        }
        System.out.println("==============================");
    }

    public static void askIdToBeEdit() {
        System.out.print("Masukkan ID yang akan diubah: ");
        int id = scanner.nextInt();
        KomoditasController.getEditForm(id);
    }

    public static void askIdToBeDelete() {
        System.out.print("Masukkan ID yang akan dihapus: ");
        int id = scanner.nextInt();
        KomoditasController.destroy(id);
    }

    public static void showEditForm(Komoditas komoditas) {
        System.out.println("===> Edit Form");
        System.out.println("Data yang ada dalam kurung adalah data lama."
                + " Isi dengan \"-\" jika tidak ingin mengubah data. ");
        System.out.print("Nama (" + komoditas.getName() + ") : ");
        String buffer = scanner.nextLine();
        String name = scanner.nextLine();

        if (!name.equals("-")) {
            komoditas.setName(name);
        }

        KomoditasController.update(komoditas);
    }
}
