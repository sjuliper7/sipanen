package Controller;

import Model.DetailPanen;
import Model.Komoditas;
import Model.Member;
import Model.Panen;
import View.KomoditasView;
import View.PanenView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.util.Pair;

public class PanenController {
    
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    
    public static void getMenu() {
        ArrayList<Panen> panens = Panen.getAll();
        PanenView.panenShowAll(panens);
        
        PanenView.menu();
    }
    
    public static void create() {
        ArrayList<Komoditas> komoditas = Komoditas.getAllData();
        KomoditasView.showAllData(komoditas);
        
        PanenView.formTambahPanen();
    }
    
    public static void store(ArrayList<Pair<Integer, Integer>> dataPanen, Integer idMember, ArrayList<String> tanggalPanens) {
        Member member = Member.findId(idMember);
        ArrayList<DetailPanen> detailPanens = null;
        Panen panen = new Panen(member, detailPanens);
        Panen.store(panen);
        DetailPanen.store(dataPanen, tanggalPanens, panen.getId());
        PanenController.getMenu();
    }
    
    public static void getEditForm() {
        PanenView.formEditPenjualan();
    }
    
    public static void edit(Integer id) {
        Panen panen = Panen.find(id);
        PanenView.formUpdate(panen);
    }
    
    public static void update(Integer idPanen, Integer idMember, ArrayList<DetailPanen> detailPanens, ArrayList<Pair<Integer, Integer>> dataPanen, ArrayList<String> tanggalPanens) {
        Panen panen = Panen.find(idPanen);
        Member member = Member.findId(idMember);
        Date date = new Date();
        panen.setMember(member);
        panen.setTanggalLapor(sdf.format(date));
        Panen.update(panen);
        DetailPanen.Update(detailPanens, dataPanen, tanggalPanens, idPanen);
        PanenController.getMenu();
    }
    
    public static void getFormDelete() {
        PanenView.formDelete();
    }
    
    public static void delete(Integer idPanen){
        Panen.delete(idPanen);
        DetailPanen.delete(idPanen);
        PanenController.getMenu();
    }
}
