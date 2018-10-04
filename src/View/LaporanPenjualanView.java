package View;

import Controller.HomeController;
import Controller.LaporanPenjualanController;
import Model.Bahan;
import Model.Member;
import Model.Penjualan;

import java.util.ArrayList;

public class LaporanPenjualanView extends View {

    public static void showMenu(){
        System.out.println("Silahkan pilih tipe laporan penjualan: ");
        System.out.println("1. Perbulan");
        System.out.println("2. Perorang");
        System.out.println("3. Perbarang");
        System.out.println("9. Kembali ke menu utama");

        System.out.print("Masukkan pilihan menu: ");
        option = scanner.nextInt();

        if(option == 1){
            LaporanPenjualanController.getFormPerbulan();
        } else if(option == 2){
            LaporanPenjualanController.getFormPerorang();
        } else if(option == 3){
            LaporanPenjualanController.getFormPerbahan();
        } else if(option == 9){
            HomeController.getHomeMenu();
        }
    }

    public static void showPerbulanForm(){
        System.out.print("Masukkan tahun laporan : ");
        int year = scanner.nextInt();
        System.out.print("Masukkan bulan laporan : ");
        int month = scanner.nextInt();

        LaporanPenjualanController.getLaporanPerbulan(year, month);
    }

    public static void showPerbahanForm(ArrayList<Bahan> bahans){
        BahanView.bahanShowAll(bahans);

        System.out.print("Masukkan ID bahan: ");
        int id = scanner.nextInt();

        LaporanPenjualanController.getLaporanPerbahan(id);
    }

    public static void showLaporan(ArrayList<Penjualan> penjualans){
        PenjualanView.penjualanShowAll(penjualans);

        LaporanPenjualanView.showMenu();
    }

    public static void showFormPerorang(ArrayList<Member> members){
        MemberView.memberShowAll(members);

        System.out.print("Masukkan ID member: ");
        int id = scanner.nextInt();

        LaporanPenjualanController.getLaporanPerorang(id);
    }
}
