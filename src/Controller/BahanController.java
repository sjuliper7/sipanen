package Controller;

import Model.Bahan;
import View.BahanView;
import java.util.ArrayList;

public class BahanController {

    public static void getMenu() {
        ArrayList<Bahan> bahans = Bahan.getAll();
        BahanView.bahanShowAll(bahans);
        BahanView.showMenu();
    }

    public static void create() {
        BahanView.showCreateForm();
    }

    public static void store(String nama, Integer qty, String deskripsi, Double harga){
        Bahan bahan = new Bahan(nama, qty, deskripsi, harga);
        Bahan.store(bahan);
        getMenu();
    }

    public static void getEditForm() {
        BahanView.editBahan();
    }

    public static void Edit(int id) {
        Bahan bahan = Bahan.findId(id);
        BahanView.updateBahan(bahan);
    }

    public static void update(Bahan bahan) {
        Bahan.update(bahan);
        getMenu();
    }

    public static void delete(int id) {
        Bahan.delete(id);
        getMenu();
    }

}
