package View;

import Controller.HomeController;
import Controller.LaporanPeminjamanController;
import Model.Alat;
import Model.Member;
import Model.Peminjaman;

import java.util.ArrayList;

public class LaporanPeminjamanView extends View {

    public static void showMenu(){
        System.out.println("Silahkan pilih tipe laporan peminjaman :");
        System.out.println("1. Perbulan");
        System.out.println("2. Perorang");
        System.out.println("3. Perbarang");
        System.out.println("9. Kembali ke menu utama");

        option = scanner.nextInt();

        if(option == 1){
            LaporanPeminjamanController.getFormPerbulan();
        } else if(option == 2){
            LaporanPeminjamanController.getFormPerorang();
        } else if(option == 3){
            LaporanPeminjamanController.getFormPerbahan();
        } else if(option == 9){
            HomeController.getHomeMenu();
        }
    }

    public static void showPerbulanForm(){
        System.out.print("Masukkan tahun laporan : ");
        int year = scanner.nextInt();
        System.out.print("Masukkan bulan laporan : ");
        int month = scanner.nextInt();

        LaporanPeminjamanController.getLaporanPerbulan(year, month);
    }

    public static void showPeralatForm(ArrayList<Alat> alats){
        AlatView.alatShowAll(alats);

        System.out.print("Masukkan ID alat : ");
        int id = scanner.nextInt();

        LaporanPeminjamanController.getLaporanPerbahan(id);
    }

    public static void showLaporan(ArrayList<Peminjaman> peminjamans){
        PeminjamanView.peminjamanShowAll(peminjamans);

        LaporanPeminjamanView.showMenu();
    }

    public static void showFormPerorang(ArrayList<Member> members){
        MemberView.memberShowAll(members);

        System.out.print("Masukkan ID member : ");
        int id = scanner.nextInt();

        LaporanPeminjamanController.getLaporanPerorang(id);
    }
}
