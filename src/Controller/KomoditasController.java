package Controller;

import Model.Komoditas;
import View.KomoditasView;

import java.util.ArrayList;

public class KomoditasController {

    public static void getSubMenu() {
        KomoditasView.showSubMenu();
    }

    public static void getCreateForm() {
        KomoditasView.showCreateForm();
    }

    public static void store(String name, String satuan) {
        Komoditas.create(name, satuan);
        KomoditasView.showSubMenu();
    }

    public static void getAllData() {
        ArrayList<Komoditas> komoditas = Komoditas.getAllData();
        KomoditasView.showAllData(komoditas);
    }

    public static void startEditProceess() {
        getAllData();
        KomoditasView.askIdToBeEdit();
        KomoditasView.showSubMenu();
    }

    public static void getEditForm(int id) {
        Komoditas komoditas = Komoditas.getOne(id);
        KomoditasView.showEditForm(komoditas);
    }

    public static void update(Komoditas komoditas) {
        Komoditas.updateOne(komoditas);
    }

    public static void destroy(int id) {
        Komoditas.delete(id);
    }

    public static void startDeleteProceess() {
        getAllData();
        KomoditasView.askIdToBeDelete();
        KomoditasView.showSubMenu();
    }
}
