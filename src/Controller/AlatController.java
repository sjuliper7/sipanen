package Controller;

import Model.Alat;
import View.AlatView;

import java.io.IOException;
import java.util.ArrayList;

public class AlatController {

    public static void getMenu() {
        ArrayList<Alat> alats;
        try {
            alats = Alat.getAll();
            AlatView.alatShowAll(alats);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        AlatView.showMenu();

    }

    public static void create() {
        AlatView.showCreateForm();
    }

    public static void store(String nama, Integer qty, String deskripsi) {
        Alat alat = new Alat(nama, qty, deskripsi);
        try {
            Alat.store(alat);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        getMenu();
    }

    public static void getEditForm() {
        AlatView.editAlat();
    }

    public static void edit(int id) {
        Alat alat = null;

        try {
            alat = Alat.findId(id);
            AlatView.updateAlat(alat);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void update(Alat alat) {
        try {
            Alat.update(alat);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        getMenu();
    }

    public static void delete(int id) {
        try {
            Alat.delete(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        getMenu();
    }
}
